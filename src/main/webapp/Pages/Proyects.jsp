<%@ page import="Model.Managers.GestionProyectos" %>
<%@ page import="DAO.DAOManager" %>
<%@ page import="Model.Managers.GestionApp" %>
<%@ page import="Model.BusinessClases.Proyecto" %>
<%@ page import="Model.BusinessClases.Usuario" %>
<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    boolean logged = session.getAttribute("username")!=null;
    DAOManager daoManager = new DAOManager();
    daoManager.open();
    GestionApp gestionApp = new GestionApp(daoManager);
    GestionProyectos gestionProyectos = gestionApp.getGestionProyectos();
    if (logged) {
        Usuario aux = (Usuario) session.getAttribute("User");
        if (session.getAttribute("class").equals("Gestor")) gestionProyectos.cargarProyectos(aux.getId(), daoManager);
    }
    else gestionProyectos.cargarProyectos(daoManager);
    %>
<html>
<head>
    <title>InBest</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<%
if (logged) {
%>
<jsp:include page="../Layouts/Sidebar.jsp"/>
<%
    }
%>

<%--Empieza para las inversiones--%>
<%
    if (session.getAttribute("enviado")!= null) {
        boolean correcto = (boolean) session.getAttribute("enviado");
        //TODO: Corregir el bug de no poder cerrar la notificacion de la confirmación del envio de las inversiones
%>
<div class="flex flex-row justify-between px-3 py-2  bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full dark:bg-cyan-900 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
    <div id="confirmation" tabindex="-1" aria-hidden="true" class="overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
        <div class="relative p-4 w-full max-w-md max-h-full">
            <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                        <%out.print(correcto?"Las inversiones han sido enviadas correctamente":"Hubo un error al enviar las inversiones, intentelo de nuevo");%>
                    </h3>
                    <button type="button" data-modal-hide="confirmation" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white">
                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                        </svg>
                        <span class="sr-only">Close modal</span>
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<%
    }
%>
<%--Termina para las inversiones--%>

<section class="mt-24 mb-5
<%
if (logged) {
%>
lg:ml-40 sm:ml-10 mr-20
 <%}%> flex lg:flex-row justify-end sm:flex-col">
    <div class="mb-3 flex items-center justify-between space-y-3 md:space-y-0">
        <form action="buscar_proyecto" method="post" class="flex flex-wrap ml-8 md:flex-row items-center justify-start space-y-4 md:space-y-0 md:space-x-2">
            <label for="orden" class="mr-2 md:mr-0 font-bold">Ordenar por:</label>
            <select id="orden" name="orden" class="mx-3 px-2 py-1 border border-slate-900 rounded-xl">
                <option value="mas_reciente">Más reciente</option>
                <option value="mas_antiguo">Más antiguo</option>
                <option value="mas_caro">Más caro</option>
                <option value="mas_barato">Más barato</option>
            </select>

            <label for="atributo" class="mr-2 md:mr-0 font-bold">Atributo:</label>
            <select id="atributo" name="atributo" class="mx-3 px-2 py-1 border border-slate-900 rounded-xl">
                <option value="nombre">Nombre</option>
                <option value="descripcion">Descripción</option>
                <option value="descripcion">Tipo</option>
                <option value="fecha_inicio">Fecha de inicio</option>
                <option value="fecha_fin">Fecha fin</option>
                <option value="cantidad_necesaria">Cantidad necesaria</option>
                <option value="cantidad_financiada">Cantidad financiada</option>
            </select>
            <input type="search" placeholder="Buscar" class="px-2 py-1 border border-gray-300 rounded-xl mr-2">
            <button type="submit" class="px-4 py-2 bg-gradient-to-r from-cyan-900 to-green-900 text-white rounded-full hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">Buscar</button>
        </form>
    </div>
</section>
<section
            <%
if (logged) {
%>class="lg:ml-40 lg:pl-36 lg:mr-10"<%}%>>
     <div>
        <ul class="mx-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            <%     for (Proyecto proyecto: gestionProyectos.getArrayProyectos()) {%>
            <li>
                <form method="post" action="${pageContext.request.contextPath}/proyectos-servlet" class="flex flex-col items-center justify-center bg-gray-100 rounded-lg p-4 mb-7">
                    <% if (logged) {%>
                    <input type="hidden" name="username" value="<% out.print(session.getAttribute("username")); %>">
                    <%}%>
                    <input type="hidden" name="codigo_proyecto" value="<% out.print(proyecto.getCodigo()); %>">
                    <button type="submit" class="w-full flex flex-col items-center">
                        <img class="h-40 max-w-full rounded-lg" src="<% out.print(proyecto.getImagen()); %>" class="tarjeta_proyecto_img">
                        <div class="text-center mt-2 w-full">
                            <h1 class="text-2xl font-bold inline-block align-middle m-3 flex flex-col justify-around leading-6" ><% out.print(proyecto.getNombre()); %>
                                <span  class="bg-green-200 text-green-700 text-sm font-semibold rounded-full px-3 mt-2 py-1 align-middle self-center"><% out.print(proyecto.getTipo()); %></span>
                            </h1>
                            <div class="flex justify-around lg:mx-0 sm:mx-1">
                                <div class="flex flex-col">
                                    <span class="text-md text-base font-medium text-blue-700 dark:text-black">Financiado: <% out.print(proyecto.getPorcentaje());%>%</span>
                                    <p><strong><% out.print(proyecto.getCantidadFinanciada()); %></strong></p>
                                </div>
                                <div class="flex flex-col">
                                    <span class="text-md font-medium text-blue-700 dark:text-black">Objetivo</span>
                                    <p><strong><% out.print(proyecto.getCantidadNecesaria()); %></strong></p>
                                </div>
                            </div>
                        </div>
                    </button>
                </form>
            </li>
            <%
                }
            %>


        </ul>
    </div>
</section>

<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
