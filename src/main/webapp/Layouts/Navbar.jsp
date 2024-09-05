<%@ page import="java.util.Properties" %>
<%@ page import="Model.BusinessClases.Usuario" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeFecha" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeHora" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.io.InputStream" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 08/06/2024
  Time: 17:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    InputStream ubicacionesYmodoInvidato = Thread.currentThread().getContextClassLoader().getResourceAsStream("FicherosDatosSistema/UbicacionesYmodoInvitado.properties");
    Properties properties = new Properties();
    properties.load(ubicacionesYmodoInvidato);
    boolean invitadoHabilitado = Boolean.parseBoolean(properties.getProperty("ModoInvitado"));
    boolean logged = (session.getAttribute("username") != null) && (session.getAttribute("isSigningIn")==null);
    Usuario aux = null;
    String ultimaFecha = String.valueOf(LocalDate.now()), ultimaHora = String.valueOf(LocalTime.now());
    if (logged) {
        aux = (Usuario) session.getAttribute("User");
        ultimaFecha = extraeFecha((LocalDateTime) session.getAttribute("ultimoLogin"));
        ultimaHora = extraeHora((LocalDateTime) session.getAttribute("ultimoLogin"));
    }
%>
<header>

    <nav class="fixed top-0 z-50 w-full bg-gradient-to-r from-cyan-900 to-green-900 border-gray-200 dark:bg-gray-900">
        <div class="px-3 py-3 lg:px-5 lg:pl-3">
            <div class="flex flex flex-wrap items-center justify-between">
                <div class="flex items-center justify-start rtl:justify-end">
                    <%
                        if (logged) {
                    %>
                    <button data-drawer-target="logo-sidebar" data-drawer-toggle="logo-sidebar"
                            aria-controls="logo-sidebar" type="button"
                            class="inline-flex items-center p-2 text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600">
                        <span class="sr-only">Open sidebar</span>
                        <svg class="w-6 h-6" aria-hidden="true" fill="currentColor" viewBox="0 0 20 20"
                             xmlns="http://www.w3.org/2000/svg">
                            <path clip-rule="evenodd" fill-rule="evenodd"
                                  d="M2 4.75A.75.75 0 012.75 4h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 4.75zm0 10.5a.75.75 0 01.75-.75h7.5a.75.75 0 010 1.5h-7.5a.75.75 0 01-.75-.75zM2 10a.75.75 0 01.75-.75h14.5a.75.75 0 010 1.5H2.75A.75.75 0 012 10z"></path>
                        </svg>
                    </button>
                    <% }
                        if (!logged) { %> <a href="${pageContext.request.contextPath}/Pages/Index.jsp"
                                             class="flex ms-2 md:me-24"><%}%>
                    <h1 class="font-extrabold tracking-tight leading-none text-gray-900 text-5xl dark:text-white text-center">
                        InBest</h1><%
                        if (!logged) { %></a><% } %>

                </div>


                <div class="flex flex-wrap overflow-hidden items-center md:order-2 space-x-3 md:space-x-0 rtl:space-x-reverse">

                    <%
                        if (logged) {
                    %>

                    <button type="button"
                            class="flex text-sm bg-gray-800 rounded-full focus:ring-4 focus:ring-gray-300 dark:focus:ring-gray-600"
                            aria-expanded="false" data-dropdown-toggle="dropdown-user">
                        <span class="sr-only">Open user menu</span>
                        <img class="w-8 h-8 rounded-full"
                             src="https://flowbite.com/docs/images/people/profile-picture-5.jpg" alt="user photo">
                    </button>
                    <div class="z-50 hidden my-4 text-base list-none bg-white divide-y divide-gray-100 rounded shadow dark:bg-gray-700 dark:divide-gray-600"
                         id="dropdown-user">
                        <div class="px-4 py-3">
                            <span class="block text-md text-gray-900 dark:text-white"><%
                                out.print(aux.getName()); %></span>
                            <span class="block text-md  text-gray-500 truncate dark:text-gray-400"><%
                                out.print(aux.getEmail()); %></span>
                            <span class="block text-md  text-gray-500 truncate dark:text-gray-400">Última vez el <% out.print(ultimaFecha); %> a las <% out.print(ultimaHora); %></span>
                        </div>
                        <ul class="py-2" aria-labelledby="user-menu-button">
                            <li>
                                <a href="${pageContext.request.contextPath}/Pages/Account.jsp"
                                   class="flex items-center px-3 py-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                                    <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                                         xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                         viewBox="0 0 24 24">
                                        <path fill-rule="evenodd"
                                              d="M12 4a4 4 0 1 0 0 8 4 4 0 0 0 0-8Zm-2 9a4 4 0 0 0-4 4v1a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2v-1a4 4 0 0 0-4-4h-4Z"
                                              clip-rule="evenodd"/>
                                    </svg>

                                    <span class="flex-1 ms-3 whitespace-nowrap">Mi cuenta</span>
                                </a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/logout-servlet"
                                   class="flex items-center px-5 py-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-gray-700 group">
                                    <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                         viewBox="0 0 18 16">
                                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                              stroke-width="2"
                                              d="M4 8h11m0 0-4-4m4 4-4 4m-5 3H3a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h3"/>
                                    </svg>
                                    <span class="flex-1 ms-3 whitespace-nowrap">Cerrar sesión</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                    <% }

                        if (!logged) {
                    %>
                    <button data-collapse-toggle="navbar-user" type="button"
                            class="inline-flex items-center p-2 w-10 h-10 justify-center text-sm text-gray-500 rounded-lg md:hidden hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-gray-200 dark:text-gray-400 dark:hover:bg-gray-700 dark:focus:ring-gray-600"
                            aria-controls="navbar-user" aria-expanded="false">
                        <span class="sr-only">Open main menu</span>
                        <svg class="w-5 h-5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                             viewBox="0 0 17 14">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M1 1h15M1 7h15M1 13h15"/>
                        </svg>
                    </button>
                    <% } %>
                </div>


                <div class="items-center justify-between hidden w-full md:flex md:w-auto md:order-1 mr-5"
                     id="navbar-user">
                    <ul class="flex flex-col font-medium p-4 md:p-0 mt-4 border border-gray-100 rounded-lg md:space-x-8 rtl:space-x-reverse md:flex-row md:mt-0 md:border-0  dark:bg-transparent md:dark:bg-transparent dark:border-gray-700">

                        <%
                            if (invitadoHabilitado && !logged) {
                        %>
                        <li>
                            <a href="${pageContext.request.contextPath}/Pages/Proyects.jsp"
                               class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                                <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                                     aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor"
                                     viewBox="0 0 22 22">
                                    <path fill-rule="evenodd"
                                          d="M11.293 3.293a1 1 0 0 1 1.414 0l6 6 2 2a1 1 0 0 1-1.414 1.414L19 12.414V19a2 2 0 0 1-2 2h-3a1 1 0 0 1-1-1v-3h-2v3a1 1 0 0 1-1 1H7a2 2 0 0 1-2-2v-6.586l-.293.293a1 1 0 0 1-1.414-1.414l2-2 6-6Z"
                                          clip-rule="evenodd"/>
                                </svg>
                                <span class="flex-1 ms-3 whitespace-nowrap">Proyectos</span>
                            </a>
                        </li>
                        <% } %>

                        <%
                            if (!logged) {
                        %>
                        <li>
                            <a href="${pageContext.request.contextPath}/Pages/Login.jsp"
                               class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                                <svg class="flex-shrink-0 w-5 h-5 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                                     aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                     viewBox="0 0 18 16">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                          stroke-width="2"
                                          d="M1 8h11m0 0L8 4m4 4-4 4m4-11h3a2 2 0 0 1 2 2v10a2 2 0 0 1-2 2h-3"/>
                                </svg>
                                <span class="flex-1 ms-3 whitespace-nowrap">Iniciar sesión</span>
                            </a>
                        </li>
                        <li>
                            <a href="${pageContext.request.contextPath}/Pages/SignIn.jsp"
                               class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                                <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                                     xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                                     viewBox="0 0 24 24">
                                    <path fill-rule="evenodd"
                                          d="M9 4a4 4 0 1 0 0 8 4 4 0 0 0 0-8Zm-2 9a4 4 0 0 0-4 4v1a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2v-1a4 4 0 0 0-4-4H7Zm8-1a1 1 0 0 1 1-1h1v-1a1 1 0 1 1 2 0v1h1a1 1 0 1 1 0 2h-1v1a1 1 0 1 1-2 0v-1h-1a1 1 0 0 1-1-1Z"
                                          clip-rule="evenodd"/>
                                </svg>

                                <span class="flex-1 ms-3 whitespace-nowrap">Registrarse</span>
                            </a>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </div>
            </div>
        </div>
    </nav>
</header>
