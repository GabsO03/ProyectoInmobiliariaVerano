<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    if (session.getAttribute("isSigningIn")!=null) {
        boolean isSigningIn = (boolean) session.getAttribute("isSigningIn");
        HashMap<String, Boolean> errores = (HashMap<String, Boolean>) request.getAttribute("errores");
        %>
<html>
<head>
    <title>InBest</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body class="bg-gradient-to-b from-cyan-950 to-green-950">
<jsp:include page="../Layouts/Navbar.jsp"/>


<section class="mt-24">
    <h1 class="p-10 mb-5 font-bold tracking-tight leading-none text-gray-900 text-5xl dark:text-white text-center"><%out.print(isSigningIn?"Regístrate":"Cambiar correo electrónico");%></h1>
    <% if (errores != null) {
        if (errores.get("wrongNumber") != null) {
    %>
    <p class="mt-2 text-center text-sm text-green-600 dark:text-green-500"><span class="font-medium">Código equivocado</span> Te hemos enviado otro, revisa tu correo electrónico.</p>
    <%
        }
    }
    %>
    <form action="${pageContext.request.contextPath}/email-servlet" method="post" class="max-w-2xl mx-auto bg-slate-900 rounded-lg flex flex-col flex-wrap p-5 m-5">
        <div class="flex flex-col">
            <div>
                <div class="mx-5 mt-5">
                    <input type="hidden" name="isSigningIn" value="<% out.print(isSigningIn);%>">
                    <%if (isSigningIn) {%>
                    <input type="hidden" name="name" value="<% out.print(session.getAttribute("name"));%>">
                    <input type="hidden" name="password" value="<% out.print(session.getAttribute("password"));%>">
                    <input type="hidden" name="tipo-usuario" value="<% out.print(session.getAttribute("tipo-usuario")); %>">
                    <%}%>
                    <input type="hidden" name="username" value="<% out.print(session.getAttribute("username")); %>">
                    <input type="hidden" name="email" value="<% out.print(session.getAttribute("email")); %>">
                    <input type="hidden" name="generatedCode" value="<% out.print(session.getAttribute("generatedCode")); %>">
                    <label name="aviso" class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Te hemos enviado un código a tu correo electrónico, no lo compartas con nadie.</label>
                    <input type="number" name="enteredCode" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="######" required />
                </div>
            </div>
            <button type="submit" class="text-white bg-indigo-500 hover:bg-indigo-950 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 mt-5 mx-5">Comprobar correo electrónico</button>
        </div>
    </form>
</section>


<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%
    }
    else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);

%>