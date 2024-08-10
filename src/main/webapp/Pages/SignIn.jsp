<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HashMap<String, Boolean> errores = (HashMap<String, Boolean>) request.getAttribute("errores");
%>
<html>
<head>
    <title>InBest | Sing in</title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body class="bg-gradient-to-b from-cyan-950 to-green-950">
<jsp:include page="../Layouts/Navbar.jsp"/>


<section class="mt-24">
    <h1 class="p-10 mb-5 font-bold tracking-tight leading-none text-gray-900 text-5xl dark:text-white text-center">Registrate</h1>
    <% if (errores != null) {
        if (errores.get("noRegistrado") != null) {
    %>
    <p class="mt-2 text-sm text-green-600 dark:text-green-500"><span class="font-medium">Lo sentimos</span> ¡Hubo un error, inténtalo de nuevo!</p>
    <%
            }
        }
    %>

    <form action="${pageContext.request.contextPath}/signin-servlet" method="post" class="max-w-2xl mx-auto bg-slate-900 rounded-lg flex flex-col flex-wrap p-5 m-5">
        <div class="flex flex-row">
            <div>
                <div class="mx-5 mt-5">
                    <label class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Nombre para mostrar</label>
                    <input type="text" name="name" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="Nombre para mostrar" required />
                </div>
                <div class="m-5">
                    <label name="username" class="block mb-2 text-md font-medium <%--text-green-700 dark:text-green-500  text-red-700 dark:text-red-500--%> text-gray-900 dark:text-white">Nombre de usuario</label>
                    <input data-popover-target="popover-username" data-popover-placement="bottom" type="text" name="username" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" required />
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
                    <%--<p class="mt-2 text-sm text-green-600 dark:text-green-500"><span class="font-medium">Enhorabuena</span> ¡Nombre de usuario disponible!</p>--%>
                    <%
                        if (errores != null) {
                            if (errores.get("yaExisteUserName") != null) {
                        %>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">!Oh no!</span> Nombre de usuario no disponible</p>
                    <%          }
                            if (errores.get("usernameInvalido") != null) {
                                %>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">!Oh no!</span> Nombre de usuario no es valido</p>
                    <%
                                }
                        }
                    %>
                </div>
                <div class="m-5">
                    <label name="email" class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Correo Electrónico</label>
                    <input type="email" name="email" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="name@email.com" required />
                </div>
            </div>
            <div>
                <div class="mt-5 m-5">
                    <label name="password" class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Contraseña</label>
                    <input type="password" name="password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="********" required />
                    <%
                        if (errores != null) {
                            if (errores.get("noCoincidence") != null) {
                    %>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500">Las contraseñas <span class="font-medium">no coinciden.</span></p>
                    <%              }
                            if (errores.get("notSafe") != null) {
                    %>
                    <p class="mt-2 text-sm text-red-600 dark:text-red-500">La contraseña no cumple con <span class="font-medium"> los requisitos.</span></p><br>
                    <%              }
                        }
                    %>

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
                <div class="m-5">
                    <label class="block mb-2 text-md font-medium text-gray-900 dark:text-white">Repite tu contraseña</label>
                    <input type="password" name="repeat-password" class="shadow-sm bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500 dark:shadow-sm-light" placeholder="********" required />
                </div>
            </div>
        </div>

        <div class="flex flex-col justify-start">
            <div class="flex flex-row justify-start">
                <div class="flex items-center ps-5">
                    <input checked id="bordered-radio-1" type="radio" value="Inversor" name="tipo-usuario" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" required>
                    <label for="bordered-radio-1" class="w-full py-4 ms-2 text-md font-medium text-gray-900 dark:text-gray-300">Soy inversor</label>
                </div>
                <div class="flex items-center pr-4 ml-5">
                    <input id="bordered-radio-2" type="radio" value="Gestor" name="tipo-usuario" class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-off set-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600" required>
                    <label for="bordered-radio-2" class="w-full py-4 ms-2 text-md font-medium text-gray-900 dark:text-gray-300">Soy gestor</label>
                </div>
            </div>
            <div class="flex justify-start items-center ml-5">
                <div class="flex items-center h-5">
                    <input id="terms" type="checkbox" value="" class="w-4 h-4 border border-gray-300 rounded bg-gray-50 focus:ring-3 focus:ring-blue-300 dark:bg-gray-700 dark:border-gray-600 dark:focus:ring-blue-600 dark:ring-offset-gray-800 dark:focus:ring-offset-gray-800" required />
                </div>
                <label for="terms" class="ms-2 text-md font-medium text-gray-900 dark:text-gray-300">Estoy de acuerdo con los <span class="text-blue-600 dark:text-blue-500">terminos y condiciones</span></label>
            </div>
            <button type="submit" class="text-white bg-indigo-500 hover:bg-indigo-950 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-md px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800 mt-5 mx-5">Siguiente</button>
            <p class="text-white self-center p-5 mb-4">¿Ya tienes una cuenta?
                <a href="${pageContext.request.contextPath}/Pages/Login.jsp" class="text-blue-600">Iniciar sesión</a>
            </p>
        </div>
    </form>
</section>


<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
