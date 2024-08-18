<%@ page import="Model.Managers.GestionInversiones" %>
<%@ page import="Model.BusinessClases.Inversion" %>
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    boolean logged = session.getAttribute("username")!=null;
    if (logged) {
        ArrayList<Inversion> inversionArrayList;
        if (session.getAttribute("resultados") != null) {
            inversionArrayList = (ArrayList<Inversion>) session.getAttribute("resultados");
        }
        else {
           inversionArrayList = (ArrayList<Inversion>) session.getAttribute("MisInversiones");
        }
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | Mis Inversiones</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>


<section
        class="<%if (logged) {%>lg:ml-36 lg:pl-28 <%}%> lg:mx-0 mt-16 md:mx-10 flex lg:justify-end md:justify-center sm:justify-center sm:mx-0 w-auto flex-col justify-center">
    <div class="flex md:flex-row items-center sm:justify-center space-y-4 md:space-y-0 lg:pt-6 lg:pb-4 px-4 lg:flex-row lg:justify-center items-center flex-wrap flex-row dark:bg-slate-200">
        <form action="${pageContext.request.contextPath}/proyectos-servlet" method="get"
              class="flex flex-row justify-between md:flex-row items-center w-full">
            <%if (logged && session.getAttribute("class").equals("Gestor")) {%><input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>"><%}%>
            <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>"/>
            <input type="hidden" name="page" value="/Pages/MyInvestments.jsp"/>
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
                           placeholder="Buscar" cadenaAux/>
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
                    <option value="ultimaActualizacion">Fecha actual</option>
                    <option value="cantidadParticipada">Participación</option>
                </select>

                <label for="orderBy" class="sr-only">Ordenar</label>
                <select id="orderBy" name="orderBy"
                        class="border border-slate-200 text-sm font-medium text-center text-slate-700 rounded-e-full border-s-gray-100 dark:border-slate-200 border-s-2 focus:ring-green-500 focus:border-green-200 block w-full px-4 dark:bg-green-900 dark:hover:bg-green-800 dark:border-gray-600 dark:placeholder-slate-900 dark:text-slate-200 dark:focus:ring-green-800 dark:focus:border-green-300">
                    <option value="codigo_Asc" selected>Ordenar:</option>
                    <option value="ultimaActualizacion_Desc">Más reciente</option>
                    <option value="ultimaActualizacion_Asc">Más antiguo</option>
                    <option value="cantidadParticipada_Desc">Más participación</option>
                    <option value="cantidadParticipada_Asc">Menos participación</option>
                </select>
            </div>
        </form>
    </div>
</section>

<section class="lg:ml-36 lg:pl-28 md:mx-0 flex lg:justify-end md:justify-center
    sm:justify-center sm:mx-0 flex-col justify-center">
    <%
        if (inversionArrayList==null || inversionArrayList.isEmpty()) {
          %><p class="text-center text-3xl text-slate-600 font-medium">No tienes inversiones para mostrar</p> <%
            }%>
    <div class="flex flex-row items-center sm:justify-center md:space-y-0 lg:pt-6 sm:pt-8 lg:pb-4 px-4 lg:justify-end items-center flex-wrap dark:bg-slate-200">
        <ul class="mx-5 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        <%
        if (inversionArrayList!=null) {
                for (Inversion inversion : inversionArrayList) {
        %>
            <li>
                <form method="post" action="${pageContext.request.contextPath}/proyectos-servlet" class="dark:bg-white">
                    <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>">
                    <input type="hidden" name="codigo_proyecto" value="<% out.print(inversion.getProyecto().getCodigo()); %>">
                    <input type="hidden" name="codigo_inversion" value="<% out.print(inversion.getCodigo()); %>">
                    <button type="submit" class="flex flex-col items-center justify-center bg-gray-100 rounded-lg p-4 mb-7">
                        <img class="h-40 max-w-full rounded-lg" src="<% out.print(inversion.getProyecto().getImagen()); %>">
                        <div class="text-center mt-2">
                            <h1 class="text-2xl font-bold inline-block align-middle m-3 flex flex-col justify-around leading-6" ><% out.print(inversion.getProyecto().getNombre()); %>
                                <span  class="bg-green-200 text-green-700 text-sm font-semibold rounded-full px-3 mt-2 py-1 align-middle self-center"><% out.print(inversion.getProyecto().getTipo()); %></span>
                            </h1>
                            <div class="flex flex-col justify-between mb-1">
                                <div class="flex flex-col">
                                    <p><strong>Cantidad con la que has participado:</strong><%out.print(inversion.getCantidadParticipada());%></p>
                                    <span class="text-md text-base font-medium text-blue-700 dark:text-black">Te corresponde el <%out.print(inversion.getPorcentajeParticipado());%>%</span>
                                </div>
                                <div class="flex flex-col">
                                    <span class="text-md font-medium text-blue-700 dark:text-black">Última inversión: <%out.print(inversion.getUltimaActualizacion());%></span>
                                </div>
                            </div>
                        </div>
                    </button>
                </form>
            </li>
        <%
                }
            }
        %>
        </ul>
    </div>
</section>



<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%
    }
    else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);

%>