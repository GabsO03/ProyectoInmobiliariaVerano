<%@ page import="Model.BusinessClases.Proyecto" %>
<%@ page import="Model.BusinessClases.Inversion" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    Proyecto proyecto = (Proyecto) session.getAttribute("proyecto");
    boolean invitadoHabilitado = (boolean) session.getAttribute("invitadoHabilitado");
    boolean logged = session.getAttribute("username") != null;
    String classs = "";
    if (invitadoHabilitado || logged) {
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | <%out.print(proyecto.getNombre());%></title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<%
    if (logged) {
        classs = (String) session.getAttribute("class");
%>
<jsp:include page="../Layouts/Sidebar.jsp"/>
<%
    }
%>

<section class="flex items-start justify-center min-h-screen mt-20 mb-5 <%
if (logged) { %> lg:ml-40 lg:pl-28 lg:p-4 <%}%>  lg:mr-0 sm:p-5">
    <div class="mx-5 bg-gradient-to-b from-cyan-900 to-green-950 rounded-xl shadow-md md:flex w-full">
        <div class="md:w-2/3 p-6 pt-0">
            <div class="text-center md:text-left">
                <h1 class="text-4xl font-bold inline-block align-middle flex items-center"><% out.print(proyecto.getNombre()); %>
                    <span
                        id="tipo"
                        class="bg-green-200 text-green-700 text-lg font-semibold rounded-full px-3 mt-9 m-8 align-middle"><%
                    out.print(proyecto.getTipo()); %></span></h1>
                <img src="<%out.print(proyecto.getImagen());%>" alt="Imagen del proyecto" class="rounded-lg">

            </div>
            <div class="mt-6">
                <div id="descripcion">
                    <h3 class="text-3xl font-bold mb-2 text-slate-100">Descripción</h3>
                    <p class="text-slate-400 text-lg"><% out.print(proyecto.getDescripcion()); %></p>
                </div>
                <div id="duracion">
                    <h3 class="text-3xl font-bold mb-2 text-slate-100">Duración</h3>
                    <p class="text-slate-400  text-lg">Inicio: <% out.print(proyecto.getFechaInicio()); %></p>
                    <p class="text-slate-400  text-lg">Finalización: <% out.print(proyecto.getFechaFin()); %></p><br>
                </div>
                <div id="financiacion">
                    <h4 class="text-3xl font-bold mb-2 text-slate-100">Financiación</h4>
                    <p class="text-slate-400 text-lg">Objetivo: <% out.print(proyecto.getCantidadNecesaria()); %></p>
                    <p class="text-slate-400 text-lg">Financiado hasta el momento: <%
                        out.print(proyecto.getCantidadFinanciada()); %></p>
                </div>
            </div>
        </div>
        <div id="card" class="p-6
            bg-transparent shadow-lg pb-10 h-35  <%out.print(logged?"lg:fixed top-24 right-4 w-1/4":"w-1/3");%>">
            <div class="mt-4">
                <p class="text-xl text-slate-200">Plazo total: <% out.print(proyecto.getPlazo()); %></p>
            </div>
            <div class="mt-4 ">
                <div class="flex flex-col justify-between mb-1">
                    <div class="flex flex-row justify-between">
                        <span class="text-lg text-base font-medium dark:text-slate-200">Financiado <%
                            out.print(proyecto.getPorcentaje()); %>%</span>
                        <span class="text-lg font-medium dark:text-slate-200">Objetivo</span>
                    </div>
                    <div class="flex flex-row justify-between text-slate-400">
                        <p><strong><% out.print(proyecto.getCantidadFinanciada()); %></strong></p>
                        <p><strong><% out.print(proyecto.getCantidadNecesaria()); %></strong></p>
                    </div>
                </div>
                <div class="w-full rounded-full h-2.5 dark:bg-slate-600">
                    <div class="bg-sky-950 h-2.5 rounded-full"
                         style="width: <% out.print(proyecto.getPorcentaje()); %>%"></div>
                </div>
            </div>
            <div class="flex flex-col items-center mt-6 space-y-4">
                <form method="post"
                      action="${pageContext.request.contextPath}/<%out.print(classs.equals("Inversor")?"investment":"create-modify-proyect");%>-servlet"
                      class="flex flex-col w-full">
                    <input type="hidden" name="codigo_proyecto" value="<%out.print(proyecto.getCodigo());%>">
                    <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>">
                    <%
                        if (logged) {
                            if (classs.equals("Inversor")) {
                                Inversion inversionAux = (Inversion) session.getAttribute("inversion");
                                if (inversionAux != null) {
                                    %>
                    <input type="hidden" name="codigo_inversion" value="<% out.print(inversionAux.getCodigo());%>">
                    <div class="flex flex-col items-center">
                        <p><strong>Cantidad con la que has participado:</strong><%out.print(inversionAux.getCantidadParticipada());%></p>
                        <span class="text-md text-base font-medium text-blue-700 dark:text-black">Te corresponde el <%out.print(inversionAux.getPorcentajeParticipado());%>%</span>
                    </div>
                    <%
                                }
                            if (proyecto.isHabilitado()) {
                    %>
                    <div class="flex flex-row justify-between">
                        <input name="cantidadEntrante" type="number"
                               class="flex-grow py-2 rounded-lg bg-sky-100 focus:outline-none focus:ring-2 focus:ring-sky-900 mr-1"
                               placeholder="Mínimo <%out.print(proyecto.getCantidadNecesaria()*0.001);%>"
                               min="<%out.print(proyecto.getCantidadNecesaria()*0.001);%>"
                               max="<%out.print(proyecto.getCantidadNecesaria() - proyecto.getCantidadFinanciada());%>">
                        <input type="submit"
                               class="px-6 py-2 bg-sky-950 text-white text-xl rounded-lg hover:bg-sky-900 transition duration-300 cursor-pointer"
                               value="Invertir">
                    </div>
                    <%
                            } else {
                    %>
                    <p class="block w-full px-4 py-2 bg-sky-250 text-gray-650 text-xl text-center rounded-lg bg-sky-200">
                        Inhabilitado</p>
                    <% }
                    } else {

                    %>
                    <div class="w-full">
                        <input type="submit"
                               class="block w-full px-4 py-2 bg-sky-950 text-white text-xl text-center rounded-lg hover:bg-sky-900 transition duration-300"
                               value="Editar">
                    </div>
                    <%
                        }
                    } else if (proyecto.isHabilitado()) {
                    %>
                    <div class="w-full">
                        <a href="${pageContext.request.contextPath}/Pages/Login.jsp"
                           class="block w-full px-4 py-2 bg-sky-950 text-white text-xl text-center rounded-lg hover:bg-sky-900 transition duration-300">Quiero
                            invertir</a>
                    </div>
                    <% } %>
                </form>
            </div>
        </div>
    </div>
</section>

<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%}%>