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
    DAOManager daoManager = new DAOManager();
    daoManager.open();
    GestionApp gestionApp = new GestionApp(daoManager);
    boolean logged = session.getAttribute("username") != null;

    if (logged || gestionApp.devuelveModoInvitado()) {
        GestionProyectos gestionProyectos = gestionApp.getGestionProyectos();
    if (session.getAttribute("resultados") != null) {
        gestionProyectos = (GestionProyectos) session.getAttribute("resultados");
    } else {
        if (logged) {
            Usuario aux = (Usuario) session.getAttribute("User");
            if (session.getAttribute("class").equals("Gestor")) {
                gestionProyectos.cargarProyectos(aux.getId(), daoManager);
            }
        } else gestionProyectos.cargarProyectos(daoManager);
    }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | <%
        if (session.getAttribute("class") != null && session.getAttribute("class").equals("Gestor")) {
            out.print("Mis proyectos");
        } else {
            out.print("Proyectos");
        }
    %></title>
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
    if (session.getAttribute("mostrarMensaje")!=null) {
%>
<jsp:include page="../Layouts/Notification.jsp"/>
<%
    }
%>
<%--Termina para las inversiones--%>
<section
        class="<%if (logged) {%>lg:ml-36 lg:pl-28 <%}%> lg:mx-0 mt-16 md:mx-10 flex lg:justify-end md:justify-center sm:justify-center sm:mx-0 w-auto flex-col justify-center">
    <div class="flex md:flex-row items-center sm:justify-center space-y-4 md:space-y-0 lg:pt-6 lg:pb-4 px-4 lg:flex-row lg:justify-center items-center flex-wrap flex-row dark:bg-slate-200">
        <form action="${pageContext.request.contextPath}/proyectos-servlet" method="get"
              class="flex flex-row justify-between md:flex-row items-center w-full">
            <%if (logged && session.getAttribute("class").equals("Gestor")) {%><input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>"><%}%>
            <input type="hidden" name="page" value="/Pages/Proyects.jsp"/>
            <label for="parametro" class="text-sm font-medium text-gray-900 dark:text-slate-800 w-full">
                <div class="relative flex">
                    <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                        <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true"
                             xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"></path>
                        </svg>
                    </div>
                    <input type="search" id="parametro" name="parametro"
                           class="block w-full p-4 ps-10 px-2 py-1 border border-gray-300 rounded-full mr-2"
                           placeholder="Buscar" required/>
                    <button type="submit"
                            class="text-white bg-gradient-to-r from-cyan-900 to-green-900 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                        Buscar
                    </button>
                </div>
            </label>
            <div class="flex lg:ml-5 sm:ml-0">
                <label for="atributo" class="sr-only">Filtro</label>
                <select id="atributo" name="atributo"
                        class="border border-slate-200 text-sm font-medium text-center text-slate-700 rounded-s-full border-s-gray-100 dark:border-slate-200 border-s-2 focus:ring-green-500 focus:border-green-200 block w-full px-4 py-2  dark:bg-cyan-900 dark:hover:bg-cyan-800 dark:border-gray-600 dark:placeholder-slate-900 dark:text-slate-200 dark:focus:ring-green-800 dark:focus:border-green-300">
                    <option value="nombre" selected>Buscar: Nombre</option>
                    <option value="nombre">Nombre</option>
                    <option value="tipo">Tipo</option>
                    <option value="fechaInicio">Inicio</option>
                    <option value="fechaFin">Finalización</option>
                    <option value="cantidadNecesaria">Objetivo</option>
                    <option value="cantidadFinanciada">Financiación</option>
                </select>

                <label for="orderBy" class="sr-only">Ordenar</label>
                <select id="orderBy" name="orderBy"
                        class="border border-slate-200 text-sm font-medium text-center text-slate-700 rounded-e-full border-s-gray-100 dark:border-slate-200 border-s-2 focus:ring-green-500 focus:border-green-200 block w-full px-4 dark:bg-green-900 dark:hover:bg-green-800 dark:border-gray-600 dark:placeholder-slate-900 dark:text-slate-200 dark:focus:ring-green-800 dark:focus:border-green-300">
                    <option value="codigo_Asc" selected>Ordenar:</option>
                    <option value="fechaInicio_Desc">Más reciente</option>
                    <option value="fechaInicio_Asc">Más antiguo</option>
                    <option value="fechaFin_Desc">Próx. venc</option>
                    <option value="fechaFin_Asc">Venc. Lejos</option>
                    <option value="cantidadNecesaria_Desc">Más caro</option>
                    <option value="cantidadNecesaria_Asc">Más barato</option>
                    <option value="cantidadFinanciada_Desc">Más financiado</option>
                    <option value="cantidadFinanciada_Asc">Menos financiado</option>
                </select>
            </div>
        </form>
    </div>
</section>

<section <%if (logged) {%>class="lg:ml-32 lg:pl-32"<%}%>>
    <%
        if (gestionProyectos.getArrayProyectos().isEmpty()) {%>
    <p class="text-center text-3xl text-slate-600 font-medium"><%
        out.print(session.getAttribute("resultados") != null? "No se encontraron resultados" : "No tienes proyectos para mostrar");%></p>
    <% }%>
    <div class="dark:bg-slate-200">
        <ul class="mx-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
            <% for (Proyecto proyecto : gestionProyectos.getArrayProyectos()) {%>
            <li>
                <form method="post" action="${pageContext.request.contextPath}/proyectos-servlet"
                      class="flex flex-col items-center justify-center bg-gray-100 rounded-lg p-4 mb-7">
                    <% if (logged) {%>
                    <input type="hidden" name="username" value="<% out.print(session.getAttribute("username")); %>">
                    <%}%>
                    <input type="hidden" name="codigo_proyecto" value="<% out.print(proyecto.getCodigo()); %>">
                    <button type="submit" class="w-full flex flex-col items-center">
                        <img class="h-40 max-w-full rounded-lg" src="<% out.print(proyecto.getImagen()); %>" alt="Imagen del proyecto">
                        <div class="text-center mt-2 w-full">
                            <h1 class="text-2xl font-bold inline-block align-middle m-3 flex flex-col justify-around leading-6"><%
                                out.print(proyecto.getNombre()); %>
                                <span class="bg-green-200 text-green-700 text-sm font-semibold rounded-full px-3 mt-2 py-1 align-middle self-center"><%
                                    out.print(proyecto.getTipo()); %></span>
                            </h1>
                            <div class="flex justify-around lg:mx-0 sm:mx-1">
                                <div class="flex flex-col">
                                    <span class="text-md text-base font-medium text-blue-700 dark:text-black">Financiado: <%
                                        out.print(proyecto.getPorcentaje());%>%</span>
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
<%}
else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);
%>