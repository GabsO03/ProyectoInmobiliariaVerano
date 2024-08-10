<%@ page import="Model.Managers.GestionInversiones" %>
<%@ page import="Model.BusinessClases.Inversion" %>
<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    boolean logged = session.getAttribute("username")!=null;
    if (logged) {
        GestionInversiones gestionInversionesAux = (GestionInversiones) session.getAttribute("MisInversiones");
%>
<html>
<head>
    <title>InBest</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>



<section class="lg:mx-0 md:mx-10 flex lg:justify-end md:justify-center
    sm:justify-center mt-2 sm:mx-0 w-full flex-col justify-center">
    <div class="flex md:flex-row items-center sm:justify-center space-y-4 md:space-y-0 lg:mt-16 lg:pt-6 lg:pb-4 px-4 sm:mt-16 lg:flex-row lg:justify-end shadow-lg shadow-slate-300 items-center flex-wrap flex-row dark:bg-sky-900">
        <form action="buscar_proyecto" method="post" class="flex flex-wrap justify-center mx-5 md:flex-row items-center lg:space-y-1 md:space-y-0 md:space-x-4">
            <label for="orden" class="mr-2 md:mr-0 font-bold">Ordenar por:</label>
            <select id="orden" name="orden" class="m-3 px-2 py-1 border border-slate-900 rounded-xl">
                <option value="mas_reciente">Más reciente</option>
                <option value="mas_antiguo">Más antiguo</option>
                <option value="mas_caro">Mayor inversión</option>
                <option value="mas_barato">Menor inversión</option>
            </select>
            <input type="search" placeholder="Buscar" class="px-2 py-1 border border-gray-300 rounded-xl mr-2">
            <button type="submit" class="px-4 py-2 bg-gradient-to-r from-cyan-900 to-green-900 text-white rounded-full hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50">Buscar</button>
        </form>
    </div>
</section>
<section class="lg:ml-36 lg:pl-28 md:mx-0 flex lg:justify-end md:justify-center
    sm:justify-center sm:mx-0 w-full flex-col justify-center">
    <div class="flex flex-row items-center sm:justify-center md:space-y-0 lg:pt-6 sm:pt-8 lg:pb-4 px-4 lg:justify-end items-center flex-wrap dark:bg-slate-200">
        <ul class="mx-10 grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        <%
            for (Inversion inversion : gestionInversionesAux.getInversiones()) {
        %>
            <li>
                <form method="post" action="${pageContext.request.contextPath}/proyectos-servlet">
                    <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>">
                    <input type="hidden" name="codigo_proyecto" value="<% out.print(inversion.getProyecto().getCodigo()); %>">
                    <input type="hidden" name="codigo_inversion" value="<% out.print(inversion.getCodigo()); %>">
                    <button type="submit" class="flex flex-col items-center justify-center bg-gray-100 rounded-lg p-4 mb-7">
                        <img class="h-auto max-w-full rounded-lg" src="/ED_PIA_SML_38.jpg" class="tarjeta_proyecto_img">
                        <div class="text-center mt-2">
                            <h1 class="text-2xl font-bold inline-block align-middle m-3" ><%out.print(inversion.getProyecto().getNombre());%><span name="tipo" class="bg-green-200 text-green-700 text-sm font-semibold rounded-full px-3 pb-1 ml-5 align-middle"><%out.print(inversion.getProyecto().getTipo());%></span></h1>
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