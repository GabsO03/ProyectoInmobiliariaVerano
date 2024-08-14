<%@ page import="java.util.Properties" %>
<%@ page import="java.io.FileReader" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 15/06/2024
  Time: 17:53
  To change this template use File | Settings | File Templates.
--%>
<%
    String classs = (String) session.getAttribute("class");
    boolean invitadoHabilitado = (boolean) session.getAttribute("invitadoHabilitado");
%>
<aside id="logo-sidebar"
       class="fixed top-0 left-0 z-40 w-64 h-screen pt-20 transition-transform -translate-x-full bg-white border-r border-gray-200 md:translate-x-0 dark:bg-gradient-to-b from-cyan-900 to-green-900 dark:border-gray-700"
       aria-label="Sidebar">
    <div class="h-full px-3 py-4 overflow-y-auto bg-white dark:bg-transparent">
        <ul class="space-y-2 font-medium">
            <li>
                <a href="${pageContext.request.contextPath}/Pages/Proyects.jsp"
                   class="flex items-center pl-1 py-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                    <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                         xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         viewBox="0 0 24 24">
                        <path fill-rule="evenodd"
                              d="M11.293 3.293a1 1 0 0 1 1.414 0l6 6 2 2a1 1 0 0 1-1.414 1.414L19 12.414V19a2 2 0 0 1-2 2h-3a1 1 0 0 1-1-1v-3h-2v3a1 1 0 0 1-1 1H7a2 2 0 0 1-2-2v-6.586l-.293.293a1 1 0 0 1-1.414-1.414l2-2 6-6Z"
                              clip-rule="evenodd"></path>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap"><%out.print(classs.equals("Gestor")? "Mis proyectos" : "Proyectos");%></span>
                </a>
            </li>
            <%
                if (classs.equals("Admin")) {
            %>
            <li>
                <button type="button" class="flex items-center w-full pl-1 pr-1.5 py-2 text-base text-gray-900 transition duration-75 rounded-lg group-hover:bg-sky-950 dark:text-white
                    dark:hover:bg-sky-950" aria-controls="opciones-panel" data-collapse-toggle="opciones-panel">
                    <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                         xmlns="http://www.w3.org/2000/svg" width="26" height="24" fill="currentColor"
                         viewBox="0 0 24 24">
                        <path fill-rule="evenodd"
                              d="M12 6a3.5 3.5 0 1 0 0 7 3.5 3.5 0 0 0 0-7Zm-1.5 8a4 4 0 0 0-4 4 2 2 0 0 0 2 2h7a2 2 0 0 0 2-2 4 4 0 0 0-4-4h-3Zm6.82-3.096a5.51 5.51 0 0 0-2.797-6.293 3.5 3.5 0 1 1 2.796 6.292ZM19.5 18h.5a2 2 0 0 0 2-2 4 4 0 0 0-4-4h-1.1a5.503 5.503 0 0 1-.471.762A5.998 5.998 0 0 1 19.5 18ZM4 7.5a3.5 3.5 0 0 1 5.477-2.889 5.5 5.5 0 0 0-2.796 6.293A3.501 3.501 0 0 1 4 7.5ZM7.1 12H6a4 4 0 0 0-4 4 2 2 0 0 0 2 2h.5a5.998 5.998 0 0 1 3.071-5.238A5.505 5.505 0 0 1 7.1 12Z"
                              clip- rule="evenodd"></path>
                    </svg>

                    <span class="flex-1 ms-2.5 text-left rtl:text-right whitespace-nowrap">Panel de control</span>
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 10 6">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="m1 1 4 4 4-4"></path>
                    </svg>
                </button>
                <ul id="opciones-panel" class="hidden py-2 space-y-2">
                    <li class="flex items-center w-full p-1 text-gray-900 transition duration-75 rounded-lg group hover:bg-sky-950 dark:text-white dark:hover:bg-sky-950">
                        <a href="${pageContext.request.contextPath}/Pages/Users.jsp"
                           class="flex items-center w-full pl-11 text-gray-900 transition duration-75 rounded-lg group hover:bg-sky-950 dark:text-white dark:hover:bg-sky-950">Ver
                            usuarios</a>
                    </li>
                    <li class="flex items-center w-full p-1 text-gray-900 transition duration-75 rounded-lg group hover:bg-sky-950 dark:text-white dark:hover:bg-sky-950">
                        <button data-modal-target="enviar-inversiones" data-modal-toggle="enviar-inversiones"
                                type="button" class="pl-11">
                            <span class="sr-only">Enviar</span>
                            <p>Enviar inversiones</p>
                        </button>
                    </li>
                </ul>
            </li>
            <%
                }
                if (classs.equals("Gestor")) {
            %>
            <li>
                <a href="${pageContext.request.contextPath}/Pages/CreateEditProyect.jsp"
                   class="flex items-center p-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                    <svg class="flex-shrink-0 w-5 h-5 mr-1 text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                         aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 20 20">
                        <path d="M5 5V.13a2.96 2.96 0 0 0-1.293.749L.879 3.707A2.96 2.96 0 0 0 .13 5H5Z"/>
                        <path d="M6.737 11.061a2.961 2.961 0 0 1 .81-1.515l6.117-6.116A4.839 4.839 0 0 1 16 2.141V2a1.97 1.97 0 0 0-1.933-2H7v5a2 2 0 0 1-2 2H0v11a1.969 1.969 0 0 0 1.933 2h12.134A1.97 1.97 0 0 0 16 18v-3.093l-1.546 1.546c-.413.413-.94.695-1.513.81l-3.4.679a2.947 2.947 0 0 1-1.85-.227 2.96 2.96 0 0 1-1.635-3.257l.681-3.397Z"/>
                        <path d="M8.961 16a.93.93 0 0 0 .189-.019l3.4-.679a.961.961 0 0 0 .49-.263l6.118-6.117a2.884 2.884 0 0 0-4.079-4.078l-6.117 6.117a.96.96 0 0 0-.263.491l-.679 3.4A.961.961 0 0 0 8.961 16Zm7.477-9.8a.958.958 0 0 1 .68-.281.961.961 0 0 1 .682 1.644l-.315.315-1.36-1.36.313-.318Zm-5.911 5.911 4.236-4.236 1.359 1.359-4.236 4.237-1.7.339.341-1.699Z"/>
                    </svg>
                    <span class="flex-1 ms-3 whitespace-nowrap">Crear proyecto</span>
                </a>
            </li>

            <%
                }
                if (classs.equals("Inversor")) {
            %>
            <li>
                <a href="${pageContext.request.contextPath}/Pages/MyInvestments.jsp"
                   class="flex items-center pl-1.5 py-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                    <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                         xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         viewBox="0 0 24 24">
                        <path d="M16.975 11H10V4.025a1 1 0 0 0-1.066-.998 8.5 8.5 0 1 0 9.039 9.039.999.999 0 0 0-1-1.066h.002Z"/>
                        <path d="M12.5 0c-.157 0-.311.01-.565.027A1 1 0 0 0 11 1.02V10h8.975a1 1 0 0 0 1-.935c.013-.188.028-.374.028-.565A8.51 8.51 0 0 0 12.5 0Z"/>
                    </svg>
                    <span class="ms-3">Mis inversiones</span>
                </a>
            </li>
            <li>
                <a href="${pageContext.request.contextPath}/Pages/CarteraVirtual.jsp"
                   class="flex items-center pl-1 py-2 text-gray-900 rounded-lg dark:text-white hover:bg-gray-100 dark:hover:bg-sky-950 group">
                    <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white"
                         xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         viewBox="0 0 24 24">
                        <path fill-rule="evenodd"
                              d="M4 5a2 2 0 0 0-2 2v10a2 2 0 0 0 2 2h16a2 2 0 0 0 2-2V7a2 2 0 0 0-2-2H4Zm0 6h16v6H4v-6Z"
                              clip-rule="evenodd"/>
                        <path fill-rule="evenodd"
                              d="M5 14a1 1 0 0 1 1-1h2a1 1 0 1 1 0 2H6a1 1 0 0 1-1-1Zm5 0a1 1 0 0 1 1-1h5a1 1 0 1 1 0 2h-5a1 1 0 0 1-1-1Z"
                              clip-rule="evenodd"/>
                    </svg>

                    <span class="flex-1 ms-3 whitespace-nowrap">Cartera virtual</span>
                </a>
            </li>
            <% } %>
            <li>
                <button type="button" class="flex items-center w-full pl-1 pr-1.5 py-2 text-base text-gray-900 transition duration-75 rounded-lg group-hover:bg-sky-950 dark:text-white
                    dark:hover:bg-sky-950" aria-controls="dropdown-example" data-collapse-toggle="dropdown-example">
                    <svg class="text-gray-500 transition duration-75 group-hover:text-gray-900 dark:text-gray-400 dark:group-hover:text-white"
                         xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor"
                         viewBox="0 0 24 24">
                        <path fill-rule="evenodd"
                              d="M9.586 2.586A2 2 0 0 1 11 2h2a2 2 0 0 1 2 2v.089l.473.196.063-.063a2.002 2.002 0 0 1 2.828 0l1.414 1.414a2 2 0 0 1 0 2.827l-.063.064.196.473H20a2 2 0 0 1 2 2v2a2 2 0 0 1-2 2h-.089l-.196.473.063.063a2.002 2.002 0 0 1 0 2.828l-1.414 1.414a2 2 0 0 1-2.828 0l-.063-.063-.473.196V20a2 2 0 0 1-2 2h-2a2 2 0 0 1-2-2v-.089l-.473-.196-.063.063a2.002 2.002 0 0 1-2.828 0l-1.414-1.414a2 2 0 0 1 0-2.827l.063-.064L4.089 15H4a2 2 0 0 1-2-2v-2a2 2 0 0 1 2-2h.09l.195-.473-.063-.063a2 2 0 0 1 0-2.828l1.414-1.414a2 2 0 0 1 2.827 0l.064.063L9 4.089V4a2 2 0 0 1 .586-1.414ZM8 12a4 4 0 1 1 8 0 4 4 0 0 1-8 0Z"
                              clip-rule="evenodd"></path>
                    </svg>

                    <span class="flex-1 ms-3 text-left rtl:text-right whitespace-nowrap">Configuración</span>
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 10 6">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="m1 1 4 4 4-4"></path>
                    </svg>
                </button>
                <ul id="dropdown-example" class="hidden py-2 space-y-2">
                    <li>
                        <a href="${pageContext.request.contextPath}/Pages/Account.jsp"
                           class="flex items-center w-full p-2 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-sky-950">Configuración
                            de perfil</a>
                    </li>
                    <%
                        if (classs.equals("Admin")) {
                    %>
                    <li class="flex items-left w-full py-2 pr-1 text-gray-900 transition duration-75 rounded-lg pl-11 group hover:bg-gray-100 dark:text-white dark:hover:bg-sky-950">
                        <button data-modal-target="activar-invitado" data-modal-toggle="activar-invitado" type="button"
                                class="text-left">
                            <span class="sr-only">Activar</span>
                            <p class=""><% out.print(invitadoHabilitado ? "Desactivar" : "Activar"); %> modo
                                invitado</p>
                        </button>
                    </li>
                    <% } %>
                </ul>
            </li>
        </ul>
    </div>
</aside>
<% if (classs.equals("Admin")) { %>
<div id="enviar-inversiones" tabindex="-1" aria-hidden="true"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <!-- Modal content -->
    <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
        <!-- Modal header -->
        <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
            <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                ¿Deseas enviar las inversiones?
            </h3>
            <button type="button"
                    class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                    data-modal-hide="enviar-inversiones">
                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                     viewBox="0 0 14 14">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                          d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                </svg>
                <span class="sr-only">Close modal</span>
            </button>
        </div>
        <!-- Modal body -->
        <div class="p-4 md:p-5 flex">
            <a href="${pageContext.request.contextPath}/send-investments-servlet" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-1 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">
                <button type="button">
                    Enviar
                </button>
            </a>
            <button type="button"
                    class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800"
                    data-modal-hide="enviar-inversiones">Cancelar
            </button>
        </div>
    </div>
</div>
</div>
<div id="activar-invitado" tabindex="-1" aria-hidden="true"
     class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
    <div class="relative p-4 w-full max-w-md max-h-full">
        <!-- Modal content -->
        <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
            <!-- Modal header -->
            <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                    ¿Deseas <% out.print(invitadoHabilitado ? "desactivar" : "activar"); %> el modo invitado?
                </h3>
                <button type="button"
                        class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                        data-modal-hide="activar-invitado">
                    <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                         viewBox="0 0 14 14">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                              d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"/>
                    </svg>
                    <span class="sr-only">Close modal</span>
                </button>
            </div>
            <!-- Modal body -->
            <div class="p-4 md:p-5 flex">
                <a href="${pageContext.request.contextPath}/modo-invitado-servlet"
                   class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-1 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">
                    <button type="button">
                        <%out.print(invitadoHabilitado ? "Desactivar" : "Activar"); %>
                    </button>
                </a>

                <button type="button"
                        class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800"
                        data-modal-hide="activar-invitado">Cancelar
                </button>
            </div>
        </div>
    </div>
</div>
<%
    }
%>