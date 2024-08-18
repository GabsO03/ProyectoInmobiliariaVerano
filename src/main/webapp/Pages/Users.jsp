<%@ page import="Model.Managers.GestionUsuarios" %>
<%@ page import="Model.BusinessClases.Usuario" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="Model.BusinessClases.Gestor" %>
<%@ page import="Model.BusinessClases.Inversor" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeFecha" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeHora" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%
    boolean logged = session.getAttribute("username")!=null;
    if (logged) {
        String ultimaFecha, ultimaHora;
        GestionUsuarios gestionUsuarios = (GestionUsuarios) session.getAttribute("UsersManager");
        HashMap<String, Usuario> usuariosHashMap = gestionUsuarios.getHashMapUsuarios();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | Ver Usuarios</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>


<section class="flex flex-col justify-center lg:ml-40 lg:pl-24">
    <div class="relative overflow-x-auto shadow-md">
        <div class="lg:mt-16 lg:pt-6 lg:pb-4 px-4 sm:mt-20 lg:flex-row justify-end sm:ml-0 shadow-lg shadow-slate-300 flex items-center justify-between flex-wrap flex-row space-y-4 md:space-y-0 bg-white dark:bg-sky-900">
            <div>
                <button id="dropdownActionButton" data-dropdown-toggle="dropdownAction" class="inline-flex items-center text-gray-500 bg-white border border-gray-300 focus:outline-none hover:bg-sky-100 focus:ring-4 focus:ring-sky-100 font-medium rounded-lg text-sm px-3 py-1.5 dark:bg-sky-800 dark:text-sky-100 dark:border-sky-600 dark:hover:bg-sky-700 dark:hover:border-cyan-600 dark:focus:ring-cyan-700" type="button">
                    <span class="sr-only">Filter button</span>
                    Filtro
                    <svg class="w-2.5 h-2.5 ms-2.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"></path>
                    </svg>
                </button>
                <!-- Dropdown menu -->
                <div id="dropdownAction" class="z-10 hidden bg-white divide-y divide-sky-100 rounded-lg shadow w-44 dark:bg-sky-700 dark:divide-gray-600">
                    <ul class="py-1 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownActionButton">
                        <li>
                            <a href="#" class="block px-4 py-2 hover:bg-cyan-100 dark:hover:bg-cyan-600 dark:hover:text-white">Nombre</a>
                        </li>
                        <li>
                            <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-cyan-600 dark:hover:text-white">Tipo</a>
                        </li>
                        <li>
                            <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-cyan-600 dark:hover:text-white">Estado</a>
                        </li>
                        <li>
                            <a href="#" class="block px-4 py-2 hover:bg-gray-100 dark:hover:bg-cyan-600 dark:hover:text-white">Última sesión</a>
                        </li>
                    </ul>
                </div>
            </div>
            <label name="table-search" class="sr-only">Search</label>
            <div class="relative">
                <div class="absolute inset-y-0 rtl:inset-r-0 start-0 flex items-center ps-3 pointer-events-none">
                    <svg class="w-4 h-4 text-slate-500 dark:text-slate-400 lg:m-0 sm:mb-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                        <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"></path>
                    </svg>
                </div>
                <input type="text" id="table-search-users" class="block p-2 ps-10 lg:m-0 sm:mb-3 text-sm text-gray-900 border border-slate-300 rounded-lg w-80 bg-sky-50 focus:ring-blue-500 focus:border-blue-500 dark:bg-sky-700 dark:border-cyan-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-sky-500 dark:focus:border-sky-500" placeholder="Search for users">
            </div>
        </div>
        <table class="w-full text-sm text-left rtl:text-right text-gray-500 dark:text-gray-400 table-auto ">
            <thead class="text-xs text-gray-700 uppercase bg-gray-50 dark:bg-sky-700 dark:text-slate-300">
            <tr>
                <th scope="col" class="px-6 py-3">
                    Nombre
                </th>
                <th scope="col" class="px-6 py-3">
                    Tipo
                </th>
                <th scope="col" class="px-6 py-3">
                    Estado
                </th>
                <th scope="col" class="px-6 py-3">
                    Último sesión
                </th>
                <th scope="col" class="px-6 py-3">
                    Modificar
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Map.Entry<String, Usuario> entry : usuariosHashMap.entrySet()) {
                    Usuario u = entry.getValue();
                    if (!u.getUsername().equals(session.getAttribute("username"))) {

                        String userClass = u.getClass().getSimpleName();
                        Gestor gestorAux;
                        Inversor inversorAux;
                        boolean bloqueado = true;
                        if (userClass.equals("Gestor")) {
                            gestorAux = (Gestor) u;
                            bloqueado = gestorAux.isBloqueado();
                        }
                        if (userClass.equals("Inversor")) {
                            inversorAux = (Inversor) u;
                            bloqueado = inversorAux.isBloqueado();
                        }
                        ultimaFecha = extraeFecha(gestionUsuarios.recuperarUltimoInicioSesionUsuario(u.getId()));
                        ultimaHora = extraeHora(gestionUsuarios.recuperarUltimoInicioSesionUsuario(u.getId()));
            %>

            <tr class="bg-white border-b dark:bg-sky-900 dark:border-sky-700 hover:bg-sky-900 dark:hover:bg-cyan-800">
                <th scope="row" class="flex items-center px-6 py-4 text-gray-900 whitespace-nowrap dark:text-white">
                    <img class="w-10 h-10 rounded-full" src="/docs/images/people/profile-picture-1.jpg" alt="Jese image">
                    <div class="ps-3">
                        <div class="text-base font-semibold"><%out.print(u.getName());%></div>
                        <div class="font-normal text-slate-300"><%out.print(u.getEmail());%></div>
                    </div>
                </th>
                <td class="px-6 py-4 text-slate-300">
                    <%out.print(userClass);%>
                </td>
                <td class="px-6 py-4">
                    <div class="flex items-center">
                        <div class="h-2.5 w-2.5 rounded-full <%out.print(bloqueado?"bg-red-500":"bg-green-500");%>  me-2"></div>
                    <%out.print(bloqueado?"Bloqueado":"Habilitado");%>
                    </div>
                </td>
                <td class="px-6 py-4">
                    <div class="ps-3">
                        <div class="text-base font-semibold"><%out.print(ultimaFecha);%></div>
                        <div class="font-normal text-slate-400"><%out.print(ultimaHora);%></div>
                    </div>
                </td>
                <td class="px-6 py-4">
                    <div class="flex justify-center">
                        <button data-modal-target="<%out.print(u.getUsername());%>" data-modal-toggle="<%out.print(u.getUsername());%>" class="text-white bg-sky-700 hover:bg-sky-800 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-2xl text-sm px-5 py-1 text-center dark:bg-sky-600 dark:hover:bg-sky-700 dark:focus:ring-sky-800"  type="button">
                            <%out.print(bloqueado?"Desbloquear":"Bloquear");%>
                        </button>
                        <div id="<%out.print(u.getUsername());%>" tabindex="-1" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                            <div class="relative p-4 w-full max-w-md max-h-full">
                                <div class="relative bg-white rounded-lg shadow dark:bg-gradient-to-r from-cyan-900 to-green-900">
                                    <button type="button" class="absolute top-3 end-2.5 text-gray-400 bg-transparent hover:bg-green-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-green-600 dark:hover:text-white" data-modal-hide="<%out.print(u.getUsername());%>">
                                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                                        </svg>
                                        <span class="sr-only">Close modal</span>
                                    </button>
                                    <div class="p-4 md:p-5 text-center">
                                        <svg class="mx-auto mb-4 text-gray-400 w-12 h-12 dark:text-gray-200" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 11V6m0 8h.01M19 10a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"></path>
                                        </svg>
                                        <h3 class="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">¿Quieres <%out.print(bloqueado?"Desbloquear":"Bloquear");%> a <%out.print(u.getName());%>?</h3>
                                        <form method="post" action="${pageContext.request.contextPath}/lock-unlock-user-servlet">
                                            <input type="hidden" name="username" value="<%out.print(u.getUsername());%>">
                                            <input type="hidden" name="class" value="<%out.print(userClass);%>">
                                            <button data-modal-hide="<%out.print(u.getUsername());%>" type="submit" class="text-white bg-red-600 hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:focus:ring-red-800 font-medium rounded-lg text-sm inline-flex items-center px-5 py-2.5 text-center">
                                                <%out.print(bloqueado?"Desbloquear":"Bloquear");%>
                                            </button>
                                            <button data-modal-hide="<%out.print(u.getUsername());%>" type="button" class="py-2.5 px-5 ms-3 text-sm font-medium text-gray-900 focus:outline-none bg-white rounded-lg border border-gray-200 hover:bg-gray-100 hover:text-blue-700 focus:z-10 focus:ring-4 focus:ring-gray-100 dark:focus:ring-green-700 dark:bg-gray-800 dark:text-gray-400 dark:border-gray-600 dark:hover:text-white dark:hover:bg-green-800">Cancelar</button>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
            <%
                        }
                    }
            %>
            </tbody>
        </table>
    </div>
</section>


<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%  }
    else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);
%>