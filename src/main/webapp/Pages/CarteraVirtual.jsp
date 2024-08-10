<%@ page import="Model.BusinessClases.Inversor" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  boolean logged = session.getAttribute("username")!=null;
  if (logged) {
    Inversor aux = (Inversor) session.getAttribute("User");

%>
<html>
<head>
  <title>InBest | Cartera Virtual</title>
  <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>

<section class="flex flex-col justify-center lg:ml-40 lg:pl-24 lg:mt-16">
  <div class="flex flex-col p-8 w-full ">
    <div class="m-8 p-10 shadow-xl shadow-slate-300  rounded-3xl">
      <h1 class="mb-2 font-bold text-3xl text-sky-700">Cartera virtual</h1>
      <h4 class="mb-2 font-bold text-lg text-sky-700">Cuentas con un saldo de <%out.print(aux.getSaldo());%></h4>
      <form method="post" action="${pageContext.request.contextPath}/self-modify-user-servlet"  class="flex flex-col">
        <label name="monto_agregar" class="text-lg font-medium">
          Ingrese el monto que desea ingresar a su cartera
        </label>
        <input type="hidden" name="action" value="add-money">
        <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>">
        <input type="number" name="monto" id="monto" min="0" class="rounded-xl my-4">
        <button type="submit" class="mx-auto bg-gradient-to-r from-cyan-900 to-green-900 py-2 px-4 rounded-full">Agregar monto</button>
      </form>
    </div>
  </div>
</section>

<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%
  }
  else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);

%>