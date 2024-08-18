<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 02/07/2024
  Time: 21:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%
    HashMap<String, Boolean> errores = (HashMap<String, Boolean>) request.getAttribute("errores");
    boolean hayError = errores != null;
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>


<body class="mx-auto mb-20 overflow-hidden bg-gradient-to-b from-cyan-950 to-green-950 bg-no-repeat pb-16">
<jsp:include page="../Layouts/Navbar.jsp"/>


<section>
    <div class="mx-auto flex flex-row justify-around flex-wrap">
        <div class="max-w-4xl m-24">
            <h1 class="mt-10 mb-5 font-bold tracking-tight leading-none text-gray-900 text-5xl dark:text-white text-center">Iniciar sesión</h1>
            <form action="${pageContext.request.contextPath}/login-servlet" method="post" class="w-full bg-white rounded-lg flex flex-col p-5 mt-10">
                <div class="mb-5">
                    <label class="block mb-2 text-md font-medium text-gray-900 dark:text-black">Usuario</label>
                    <label>
                        <input type="text" name="username" class="bg-gray-50 border border-gray-300 text-gray-900 text-md rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="usuario" required />
                    </label>
                    <% if (hayError) {
                        if (errores.get("noCoincidence") != null) {%>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">¡Oh no!</span> El usuario y la contraseña no coinciden.</p>
                    <%      }
                        if (errores.get("isBloqueado") != null) {
                            %>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">¡Oh no!</span> Tu cuenta está bloqueada.</p>
                    <%
                            }
                        }
                    %>

                </div>
                <div class="mb-5">
                    <label class="block mb-2 text-md font-medium text-gray-900 dark:text-black">Contraseña</label>
                    <input type="password" name="password" class="bg-indigo-500 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="********" required />
                </div>
                <%--
                <div class="flex items-start mb-5">
                    <div class="flex items-center h-5">
                        <input id="remember" type="checkbox" value="" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800" required />
                    </div>
                    <label for="remember" class="ms-2 text-sm font-medium dark:text-indigo-950 hover:cursor-pointer">Remember me</label>
                </div>--%>
                <button type="submit" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Iniciar sesión</button>
                <div class="text-sm font-medium text-gray-900 dark:text-gray m-5 text-center">
                    ¿No tienes cuenta? <a href="SignIn.jsp" class="text-blue-600 hover:underline dark:text-blue-500"> Crea una cuenta</a>
                </div>
            </form>
        </div>
    </div>
</section>


<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
