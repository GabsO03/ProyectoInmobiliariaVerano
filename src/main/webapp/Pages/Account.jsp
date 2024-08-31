<%@ page import="Model.Managers.GestionUsuarios" %>
<%@ page import="Model.BusinessClases.Usuario" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    HashMap<String, Boolean> errores = (HashMap<String, Boolean>) request.getAttribute("errores");
    boolean error = errores != null;
    boolean noEsContrasenia = error&&(errores.get("notLogged")!=null);
    boolean logged = session.getAttribute("username")!=null;
    if (logged) {
        String username = String.valueOf(session.getAttribute("username"));
        boolean correcto = false;
        if (session.getAttribute("change_password")!=null) correcto = (boolean) session.getAttribute("change_password");
        Usuario aux = (Usuario) session.getAttribute("User");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | Mi Cuenta</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body>
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>

<%
    if (session.getAttribute("mostrarMensaje")!=null) {
%>
<jsp:include page="../Layouts/Notification.jsp"/>
<%
    }
%>

<div class="items-center lg:mx-28 px-20 pt-12">
    <section class="lg:mx-20 my-10 p-10 self-center rounded-lg bg-indigo-950">
        <div class="flex mb-5">
            <h1 class="bg-blue-500 w-8 h-8 rounded-full text-center text-3xl font-extrabold leading-none mr-2"><% out.print(aux.getName().charAt(0)); %></h1>
            <div class="bg-transparent flex flex-row">
                <h1 class="font-bold text-3xl text-white self-start leading-7"><% out.print(aux.getName()); %></h1><span id="tipo" class="bg-green-200 text-green-700 text-lg font-semibold rounded-full px-3 pb-1 ml-5 align-middle"><%out.print(session.getAttribute("class"));%></span>
            </div>
        </div>
        <div class="flex flex-col">
            <div class="flex flex-col justify-between w-full mb-4">
                <label name="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">NOMBRE SIMPLE</label>
                <div class="flex flex-row justify-between px-3 py-2  bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full dark:bg-cyan-900 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <input type="text" name="disabled-input" aria-label="disabled input" class="bg-transparent border-0 p-0" value="<% out.print(aux.getName()); %>" disabled>
                    <button data-modal-target="change-name" data-modal-toggle="change-name" type="button">
                        <span class="sr-only">Editar</span>
                        <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                            <path fill-rule="evenodd" d="M14 4.182A4.136 4.136 0 0 1 16.9 3c1.087 0 2.13.425 2.899 1.182A4.01 4.01 0 0 1 21 7.037c0 1.068-.43 2.092-1.194 2.849L18.5 11.214l-5.8-5.71 1.287-1.31.012-.012Zm-2.717 2.763L6.186 12.13l2.175 2.141 5.063-5.218-2.141-2.108Zm-6.25 6.886-1.98 5.849a.992.992 0 0 0 .245 1.026 1.03 1.03 0 0 0 1.043.242L10.282 19l-5.25-5.168Zm6.954 4.01 5.096-5.186-2.218-2.183-5.063 5.218 2.185 2.15Z" clip-rule="evenodd"></path>
                        </svg>
                    </button>
                    <div id="change-name" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                        <div class="relative p-4 w-full max-w-md max-h-full">
                            <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                                        Cambia tu nombre simple
                                    </h3>
                                    <button type="button" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="change-name">
                                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                                        </svg>
                                        <span class="sr-only">Close modal</span>
                                    </button>
                                </div>
                                <div class="p-4 md:p-5">
                                    <form method="post" action="${pageContext.request.contextPath}/self-modify-user-servlet" class="space-y-4">
                                        <input type="hidden" name="action" value="change-name">
                                        <input type="hidden" name="username" value="<%out.print(aux.getUsername());%>">
                                        <div>
                                            <label name="newName" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Introduce tu nuevo nombre simple</label>
                                            <input type="text" name="newName" id="newName" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="new_name" required />
                                        </div>
                                        <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">Guardar cambios</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex flex-col justify-between w-full mb-4">
                <label name="username" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">NOMBRE DE USUARIO</label>
                <div class="flex flex-row justify-between px-3 py-2  bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full dark:bg-cyan-900 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-slate-900">
                    <input type="text" name="disabled-input" aria-label="disabled input" class="bg-transparent border-0 p-0" value="<% out.print(aux.getUsername()); %>" disabled>
                    <!-- Modal toggle -->
                    <button data-modal-target="change-username" data-modal-toggle="change-username" type="button">
                        <span class="sr-only">Editar</span>
                        <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                            <path fill-rule="evenodd" d="M14 4.182A4.136 4.136 0 0 1 16.9 3c1.087 0 2.13.425 2.899 1.182A4.01 4.01 0 0 1 21 7.037c0 1.068-.43 2.092-1.194 2.849L18.5 11.214l-5.8-5.71 1.287-1.31.012-.012Zm-2.717 2.763L6.186 12.13l2.175 2.141 5.063-5.218-2.141-2.108Zm-6.25 6.886-1.98 5.849a.992.992 0 0 0 .245 1.026 1.03 1.03 0 0 0 1.043.242L10.282 19l-5.25-5.168Zm6.954 4.01 5.096-5.186-2.218-2.183-5.063 5.218 2.185 2.15Z" clip-rule="evenodd"></path>
                        </svg>
                    </button>

                    <!-- Main modal -->
                    <div id="change-username" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                        <div class="relative p-4 w-full max-w-md max-h-full">
                            <!-- Modal content -->
                            <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                                <!-- Modal header -->
                                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                                        Cambia tu nombre de usuario
                                    </h3>
                                    <button type="button" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="change-username">
                                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                                        </svg>
                                        <span class="sr-only">Close modal</span>
                                    </button>
                                </div>
                                <!-- Modal body -->
                                <div class="p-4 md:p-5">
                                    <form method="post" action="${pageContext.request.contextPath}/self-modify-user-servlet" class="space-y-4">
                                        <input type="hidden" name="action" value="change-username">
                                        <input type="hidden" name="username" value="<%out.print(username);%>">
                                        <div>
                                            <label name="newUsername" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Introduce tu nuevo nombre de usuario</label>
                                            <input data-popover-target="popover-username" data-popover-placement="bottom" type="text" name="newUsername" id="newUsername" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="new_username" required />
                                            <div data-popover id="popover-username" role="tooltip" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
                                                <div class="p-3 space-y-2">
                                                    <h3 class="font-semibold text-gray-900 dark:text-white">Ten en cuenta estas condiciones:</h3>
                                                    <ul>
                                                        <li class="flex items-center mb-1">
                                                            Debe tener una longitud entre 6 y 16 caracteres
                                                        </li>
                                                        <li class="flex items-center mb-1">
                                                            No debe comenzar ni terminar por un '_' o un '.'
                                                        </li>
                                                        <li class="flex items-center mb-1">
                                                            No debe incluir espacios ni dos '_' o '.' consecutivos
                                                        </li>
                                                        <li class="flex items-center mb-1">
                                                            Puede incluir letras, números, '_' o '.'
                                                        </li>
                                                    </ul>
                                                </div>
                                                <div data-popper-arrow></div>
                                            </div>
                                            <%
                                                if (error&&(errores.get("yaExisteUserName") != null)) {%>
                                            <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">!Oh no!</span> Nombre de usuario no disponible</p>
                                            <%          }
                                                if (error&&(errores.get("usernameInvalido") != null)) {%>
                                            <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">!Oh no!</span> Nombre de usuario no es valido</p>
                                            <%          }%>
                                        </div>
                                        <div>
                                            <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Introduce tu contraseña</label>
                                            <input type="password" name="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
                                            <%if (noEsContrasenia) {%><p class="mt-2 text-sm text-red-600 dark:text-red-500">Las contraseña <span class="font-medium">es incorrecta.</span></p><%}%>
                                        </div>
                                        <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">Guardar cambios</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="flex flex-col justify-between w-full mb-4">
                <label name="email" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">CORREO ELECTRONICO</label>
                <div class="flex flex-row justify-between px-3 py-2  bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full  dark:bg-cyan-900 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500">
                    <input type="email" id="disabled-input" aria-label="disabled input" class="bg-transparent border-0 p-0" value="<% out.print(aux.getEmail()); %>" disabled>
                    <button data-modal-target="change-email" data-modal-toggle="change-email" type="button">
                        <span class="sr-only">Editar</span>
                        <svg class="text-gray-500 transition duration-75 dark:text-gray-400 group-hover:text-gray-900 dark:group-hover:text-white" xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 24 24">
                            <path fill-rule="evenodd" d="M14 4.182A4.136 4.136 0 0 1 16.9 3c1.087 0 2.13.425 2.899 1.182A4.01 4.01 0 0 1 21 7.037c0 1.068-.43 2.092-1.194 2.849L18.5 11.214l-5.8-5.71 1.287-1.31.012-.012Zm-2.717 2.763L6.186 12.13l2.175 2.141 5.063-5.218-2.141-2.108Zm-6.25 6.886-1.98 5.849a.992.992 0 0 0 .245 1.026 1.03 1.03 0 0 0 1.043.242L10.282 19l-5.25-5.168Zm6.954 4.01 5.096-5.186-2.218-2.183-5.063 5.218 2.185 2.15Z" clip-rule="evenodd"></path>
                        </svg>
                    </button>

                    <div id="change-email" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                        <div class="relative p-4 w-full max-w-md max-h-full">
                            <!-- Modal content -->
                            <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                                <!-- Modal header -->
                                <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                                    <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                                        Cambia tu correo electrónico
                                    </h3>
                                    <button type="button" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="change-email">
                                        <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                            <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                                        </svg>
                                        <span class="sr-only">Close modal</span>
                                    </button>
                                </div>
                                <!-- Modal body -->
                                <div class="p-4 md:p-5">
                                    <form method="post" action="${pageContext.request.contextPath}/self-modify-user-servlet" class="space-y-4">
                                        <input type="hidden" name="action" value="change-email">
                                        <input type="hidden" name="username" value="<%out.print(username);%>">
                                        <div>
                                            <label name="newEmail" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Introduce tu nuevo correo eletrónico</label>
                                            <input type="email" name="newEmail" id="newEmail" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" placeholder="newemail@email.com" required />
                                        </div>
                                        <p class="mt-2 text-sm text-green-600 dark:text-gray-200"><span class="font-medium">Revisa tu correo.</span> Te enviaremos el código de verificación</p>

                                        <div>
                                            <label for="password" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Introduce tu contraseña</label>
                                            <input type="password" name="password" id="password" placeholder="••••••••" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-600 dark:border-gray-500 dark:placeholder-gray-400 dark:text-white" required />
                                            <%if (noEsContrasenia) {%><p class="mt-2 text-sm text-red-600 dark:text-red-500">Las contraseña <span class="font-medium">es incorrecta.</span></p><%}%>
                                        </div>
                                        <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">Guardar cambios</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="mt-5">
            <button data-modal-target="change-password" data-modal-toggle="change-password" type="button" class="border border-slate-900 bg-gradient-to-r from-cyan-900 to-green-900 rounded-full px-4 py-1 font-bold text-sky-100">
                Cambiar contraseña
            </button>
            <%if (session.getAttribute("change_password")!=null){%><p class="text-lg text-slate-500 text-medium text-left"><%out.print(correcto?"La contraseña ha sido cambiada correctamente.": "Hubo un error al intentar cambiar la contraseña");%></p><%}%>
            <div id="change-password" tabindex="-1" aria-hidden="true" class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
                <div class="relative p-4 w-full max-w-md max-h-full">
                    <!-- Modal content -->
                    <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                        <!-- Modal header -->
                        <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                            <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                                Cambia tu contraseña
                            </h3>
                            <button type="button" class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white" data-modal-hide="change-password">
                                <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 14 14">
                                    <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                                </svg>
                                <span class="sr-only">Close modal</span>
                            </button>
                        </div>
                        <!-- Modal body -->
                        <div class="p-4 md:p-5">
                            <form method="post" action="${pageContext.request.contextPath}/self-modify-user-servlet" class="space-y-4">
                                <input type="hidden" name="action" value="change-password">
                                <input type="hidden" name="username" value="<%out.print(username);%>">
                                <div class="mx-3">
                                        <label name="old-password" class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Introduce tu actual contraseña</label>
                                        <input type="password" name="old-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="********" required />
                                        <%if (noEsContrasenia) {%><p class="mt-2 text-sm text-red-600 dark:text-red-500">Las contraseña <span class="font-medium">es incorrecta.</span></p><%}%>

                                        <label name="new-password" class="block my-3 text-md font-medium text-gray-900 dark:text-white">Introduce tu nueva contraseña</label>
                                        <input type="password" name="new-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="********" required />
                                        <%if (error&&(errores.get("noCoincidence") != null)) {%><p class="mt-2 text-sm text-red-600 dark:text-red-500">Las contraseñas <span class="font-medium">no coinciden.</span></p><%}%>
                                        <%if (error&&(errores.get("notSafe") != null)) {%><p class="mt-2 text-sm text-red-600 dark:text-red-500">La contraseña no cumple con <span class="font-medium"> los requisitos.</span></p><br><%}%>

                                        <div data-popover id="popover-password" role="tooltip" class="absolute z-10 invisible inline-block text-sm text-gray-500 transition-opacity duration-300 bg-white border border-gray-200 rounded-lg shadow-sm opacity-0 w-72 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-400">
                                            <div class="p-3 space-y-2">
                                                <h2 class="mb-2 text-sm font-semibold text-gray-900 dark:text-white">Tu contraseña debe contener al menos:</h2>
                                                <ul class=" space-y-1 text-gray-500 list-disc list-inside dark:text-gray-400 text-sm">
                                                    <li class="flex items-center mb-1">
                                                        - Al menos 8 caracteres (hasta 100)
                                                    </li>
                                                    <li class="flex items-center mb-1">
                                                        - Al menos una letra mayúscula o minúscula
                                                    </li>
                                                    <li class="flex items-center mb-1">
                                                        - Al menos un dígito (0 - 9)
                                                    </li>
                                                    <li class="flex items-center mb-1">
                                                        - Al menos un caracter especial ( @ - _ * + . $ # )
                                                    </li>
                                                    <li class="flex items-center mb-1">
                                                        - Ningún espacio en blanco
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>
                                        <div class="mb-5">
                                            <label name="repeat-password" class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Repite tu contraseña</label>
                                            <input type="password" name="repeat-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="********" required />
                                        </div>
                                    </div>
                                <button type="submit" class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">Guardar cambios</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>

<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%
    }
    else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);

%>