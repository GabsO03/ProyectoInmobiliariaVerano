<%@ page import="Model.Managers.GestionUsuarios" %>
<%@ page import="Model.BusinessClases.Usuario" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="Model.BusinessClases.Gestor" %>
<%@ page import="Model.BusinessClases.Inversor" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeFecha" %>
<%@ page import="static Model.Biblioteca.FuncionesCadenas.extraeHora" %>
<%@ page import="DAO.DAOManager" %><%--
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
        HashMap<String, Usuario> usuariosHashMap;
        if (session.getAttribute("busqueda")==null) {
            DAOManager daoManager = new DAOManager();
            daoManager.open();
            gestionUsuarios.cargarUsuarios(daoManager);
            daoManager.close();
        }
        usuariosHashMap = gestionUsuarios.getHashMapUsuarios();

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
        <form method="get" action="${pageContext.request.contextPath}/user-control-servlet" class="lg:mt-16 lg:pt-6 lg:pb-4 px-4 sm:mt-20 lg:flex-row justify-end sm:ml-0 shadow-lg shadow-slate-300 flex items-center justify-between flex-wrap flex-row space-y-4 md:space-y-0 bg-white dark:bg-slate-200">
            <label for="parameter" class="text-sm font-medium text-gray-900 dark:text-slate-800 w-5/6">
                <div class="relative flex">
                    <div class="absolute inset-y-0 start-0 flex items-center ps-3 pointer-events-none">
                        <svg class="w-4 h-4 text-gray-500 dark:text-gray-400" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"></path>
                        </svg>
                    </div>
                    <input type="search" id="parameter" name="parameter" class="block w-full p-4 ps-10 px-2 py-1 border border-gray-300 rounded-full mr-2" placeholder="Buscar" required/>
                    <button type="submit" class="text-white bg-gradient-to-r from-cyan-900 to-green-900 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Buscar</button>
                </div>
            </label>
            <button id="dropdownActionButton" data-dropdown-toggle="dropdownAction" class="flex text-white bg-gradient-to-r from-cyan-900 to-green-900 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-full text-sm px-4 py-2 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800" type="button">
                <span class="sr-only">Filter button</span>
                Filtrar por:
                <svg class="w-2.5 h-2.5 ms-2.5 mt-1.5" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 10 6">
                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 4 4 4-4"></path>
                </svg>
            </button>
            <!-- Dropdown menu -->
            <div id="dropdownAction" class="z-10 hidden bg-white divide-y divide-sky-100 rounded-lg shadow w-44 dark:bg-sky-700 dark:divide-gray-600">
                <ul class="py-1 text-sm text-gray-700 dark:text-gray-200" aria-labelledby="dropdownActionButton">
                    <li>
                        <select id="tipo_nombre" name="tipo_nombre" class="
                            text-md focus:ring-green-500 w-full px-4 dark:bg-sky-700 dark:placeholder-slate-900  dark:focus:ring-green-800
                            border-none decoration-none
                            block px-4 py-2 hover:bg-cyan-100 dark:hover:bg-cyan-600 dark:hover:text-white">
                            <option value="name" selected>Nombre simple</option>
                            <option value="userName">Nombre de usuario</option>
                        </select>
                    </li>
                    <li>
                        <select id="tipo" name="tipo" class="
                            text-md focus:ring-green-500 w-full px-4 dark:bg-sky-700 dark:placeholder-slate-900  dark:focus:ring-green-800
                            border-none decoration-none
                            block px-4 py-2 hover:bg-cyan-100 dark:hover:bg-cyan-600 dark:hover:text-white">
                            <option value="Usuario" selected>Tipo</option>
                            <option value="Admin">Admin</option>
                            <option value="Gestor">Gestor</option>
                            <option value="Inversor">Inversor</option>
                        </select>
                    </li>
                    <li>
                        <select id="estado" name="estado" class="
                            text-md focus:ring-green-500 w-full px-4 dark:bg-sky-700 dark:placeholder-slate-900  dark:focus:ring-green-800
                            border-none decoration-none
                            block px-4 py-2 hover:bg-cyan-100 dark:hover:bg-cyan-600 dark:hover:text-white">
                            <option value="0" selected>Estado</option>
                            <option value="0">Habilitado</option>
                            <option value="1">Bloqueado</option>
                        </select>
                    </li>
                    <li>
                        <select id="lastLogin" name="lastLogin" class="
                            text-md focus:ring-green-500 w-full px-4 dark:bg-sky-700 dark:placeholder-slate-900  dark:focus:ring-green-800
                            border-none decoration-none
                            block px-4 py-2 hover:bg-cyan-100 dark:hover:bg-cyan-600 dark:hover:text-white">
                            <option value="always" selected>Última sesión</option>
                            <option value="today">Hoy</option>
                            <option value="month">Este mes</option>
                            <option value="year">Este año</option>
                            <option value="always">Cualquiera</option>
                        </select>
                    </li>
                </ul>
            </div>
        </form>


        <table class="w-full text-sm text-left rtl:text-right dark:text-slate-800 table-auto ">
            <thead class="text-xs text-slate-700 uppercase bg-slate-50 dark:bg-white dark:text-slate-800">
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
                    Última sesión
                </th>
                <th scope="col" class="px-6 py-3">
                    Modificar
                </th>
            </tr>
            </thead>
            <%
                if (usuariosHashMap.isEmpty()) {%>
            <p class="text-center text-3xl text-slate-600 font-medium"><%
                out.print("No se encontraron resultados");%></p>
            <% }%>
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

            <tr class="bg-white border-b dark:bg-slate-100 dark:border-sky-700 hover:bg-sky-900 dark:hover:bg-cyan-800">
                <th scope="row" class="flex items-center px-6 py-4 whitespace-nowrap dark:text-slate-900">
                    <img class="w-10 h-10 rounded-full" src="/docs/images/people/profile-picture-1.jpg" alt="Jese image">
                    <div class="ps-3">
                        <div class="text-base font-semibold"><%out.print(u.getName() + " (" + u.getUsername() + ")");%></div>
                        <div class="font-normal text-slate-800"><%out.print(u.getEmail());%></div>
                    </div>
                </th>
                <td class="px-6 py-4 text-slate-700">
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
                        <div class="font-normal text-slate-700"><%out.print(ultimaHora);%></div>
                    </div>
                </td>
                <td class="px-6 py-4">
                    <div class="flex justify-center">
                        <button data-modal-target="<%out.print(u.getUsername());%>" data-modal-toggle="<%out.print(u.getUsername());%>" class="text-white hover:bg-sky-800 focus:ring-4 focus:outline-none focus:ring-sky-300 font-medium rounded-2xl text-sm px-5 py-1 text-center dark:bg-sky-800 dark:hover:bg-sky-700 dark:focus:ring-sky-800"  type="button">
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
                                        <form method="post" action="${pageContext.request.contextPath}/user-control-servlet">
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