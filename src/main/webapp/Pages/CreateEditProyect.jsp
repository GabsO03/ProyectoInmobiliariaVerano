<%@ page import="Model.BusinessClases.Proyecto" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.util.HashMap" %>
<%--
  Created by IntelliJ IDEA.
  User: pollo
  Date: 04/07/2024
  Time: 12:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    boolean logged = session.getAttribute("username") != null;
    boolean isModifying = false;
    Proyecto proyectoParaModificar = null;
    if (logged) {
        HashMap<String, Boolean> errores = (HashMap<String, Boolean>) request.getAttribute("errores");
        //TODO PONER LO DE LA FECHAAAA
        if (session.getAttribute("modifyProyect") != null)
            isModifying = (boolean) session.getAttribute("modifyProyect");
        if (isModifying) proyectoParaModificar = (Proyecto) session.getAttribute("proyecto");
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>InBest | <%out.print(isModifying ? "Modificar Proyecto" : "Crear Proyecto");%></title>
    <jsp:include page="../Scripts/HeaderScripts.jsp"/>
</head>

<body class="bg-gray-100">
<jsp:include page="../Layouts/Navbar.jsp"/>
<jsp:include page="../Layouts/Sidebar.jsp"/>
<%
    if (session.getAttribute("mostrarMensaje")!=null) {
%>
<jsp:include page="../Layouts/Notification.jsp"/>
<%
    }
%>

<section class="py-24 px-10 bg-white mx-auto max-w-3xl shadow-lg">
    <h1 class="text-3xl font-bold mb-8 text-center"><%
        out.print(isModifying ? "Modificar proyecto" : "Crear nuevo proyecto");%></h1>
    <form action="${pageContext.request.contextPath}/<%out.print(isModifying?"modify":"create");%>-proyect-servlet"
          method="post" enctype="multipart/form-data" class="max-w-3xl mx-auto">
        <%if (isModifying) { %><input type="hidden" name="codigo_proyecto"
                                      value="<%out.print(proyectoParaModificar.getCodigo());%>"><%}%>
        <input type="hidden" name="username" value="<%out.print(session.getAttribute("username"));%>">
        <% if (isModifying) {%>
        <img src="<%out.print(proyectoParaModificar.getImagen());%>" alt="Vista previa de la imagen">
        <%}%>
        <label class="block mb-2 text-sm font-medium text-gray-900 dark:text-white" name="user_avatar">Sube una
            imagen</label>
        <label for="imagen" class="block my-3">
            <input type="file" id="imagen" name="imagen" accept="image/*" <%out.print(isModifying?"":"required ");%>class="hidden">
            <span class="bg-sky-950 hover:bg-sky-900 text-white py-2 px-4 mr-5 rounded-lg text-lg cursor-pointer">Seleccionar imagen</span>
            <span class="text-sm text-red-500 text-italic font-bold">Obligatorio</span>
        </label>
        <div class="mt-1 text-md text-slate-900 dark:text-slate-900" id="user_avatar_help">Sube una imagen para que tus
            inversores sepan en que están invirtiendo
        </div>

        <label for="nombre-proyecto" class="block mt-4 text-2xl font-bold">
            Nombre <span class="text-sm text-red-500 text-italic">Obligatorio</span>
            <input type="text" id="nombre-proyecto"
                   name="nombre-proyecto"<%out.print(isModifying?" value=\"" + proyectoParaModificar.getNombre() + "\"":" ");%>
                   required class="block w-full border border-gray-300 rounded-lg px-4 py-2 mt-2">
            <%--if (errores!=null && errores.get("yaExisteNombre")!=null){%><p class="text-red-600 text-sm text-light mx-5 my-2d">Ya existe un proyecto con ese nombre</p><%}--%>
        </label>
        <label for="tipo" class="block mt-4 text-2xl font-bold">
            Tipo
            <div id="tipo" class="flex flex-row justify-start">
                <div class="flex items-center ps-5">
                    <input<%if (isModifying) out.print(proyectoParaModificar.getTipo().equals("Alquiler")?" checked":""); else out.print(" checked");%>
                            id="bordered-radio-1" type="radio" value="Alquiler" name="tipo-proyecto"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-offset-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                            required>
                    <label for="bordered-radio-1"
                           class="w-full py-4 ms-2 text-md font-medium text-slate-900 dark:text-slate-900">Alquiler</label>
                </div>
                <div class="flex items-center pr-4 ml-5">
                    <input<%if (isModifying) out.print(proyectoParaModificar.getTipo().equals("Plusvalía")?" checked":"");%>
                            id="bordered-radio-2" type="radio" value="Plusvalía" name="tipo-proyecto"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-off set-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                            required>
                    <label for="bordered-radio-2"
                           class="w-full py-4 ms-2 text-md font-medium text-slate-900 dark:text-slate-900">Plusvalía</label>
                </div>
                <div class="flex items-center pr-4 ml-5">
                    <input<%if (isModifying) out.print(proyectoParaModificar.getTipo().equals("Préstamo")?" checked":"");%>
                            id="bordered-radio-3" type="radio" value="Préstamo" name="tipo-proyecto"
                            class="w-4 h-4 text-blue-600 bg-gray-100 border-gray-300 focus:ring-blue-500 dark:focus:ring-blue-600 dark:ring-off set-gray-800 focus:ring-2 dark:bg-gray-700 dark:border-gray-600"
                            required>
                    <label for="bordered-radio-3"
                           class="w-full py-4 ms-2 text-md font-medium text-slate-900 dark:text-slate-900">Préstamo</label>
                </div>
            </div>
        </label>
        <label for="descripcion" class="block mt-4 text-2xl font-bold">
            Descripción
            <textarea id="descripcion" name="descripcion"
                      class="block w-full border border-gray-300 rounded-lg px-4 py-2 mt-2"><%
                    if (isModifying) out.print(proyectoParaModificar.getDescripcion());
                %></textarea>
        </label>
        <label for="fechaInicio" class="block mt-4 text-2xl font-bold">
            Fecha de Inicio <span class="text-sm text-red-500 text-italic">Obligatorio</span>
            <input type="date" id="fechaInicio"
                   name="fechaInicio" <%out.print(isModifying?" value=\"" + proyectoParaModificar.getFechaInicio() + "\"" : "min=\"" + LocalDate.now() + "\"");%>
                   required class="block w-full border border-gray-300 rounded-lg px-4 py-2 mt-2">
        </label>
        <label for="fechaFin" class="block mt-4 text-2xl font-bold">
            Fecha de Fin <span class="text-sm text-red-500 text-italic">Obligatorio</span>
            <input type="date" id="fechaFin"
                   name="fechaFin" <%out.print(isModifying?" value=\"" + proyectoParaModificar.getFechaFin() + "\"" : "min=\"" + LocalDate.now().plusDays(1) + "\"");%>
                   required class="block w-full border border-gray-300 rounded-lg px-4 py-2 mt-2">
            <%--if (errores!=null && errores.get("fechaFinInvalida")!=null){%><p class="text-red-600 text-sm text-light mx-5 my-2d">La fecha de finalización debe ser anterior a la fecha de inicio</p><%}--%>
        </label>
        <label for="cantidadNecesaria" class="block mt-4 text-2xl font-bold">
            Cantidad Necesaria <span class="text-sm text-red-500 text-italic">Obligatorio</span>
            <input type="number" id="cantidadNecesaria"
                   name="cantidadNecesaria"<%out.print(isModifying?" value='" + proyectoParaModificar.getCantidadNecesaria() + "' min='" + proyectoParaModificar.getCantidadFinanciada() + "'":"");%>
                   required class="block w-full border border-gray-300 rounded-lg px-4 py-2 mt-2">
        </label>
        <%
            if (isModifying) {
        %>
        <div class="mt-4">
            <button class="bg-sky-950 text-white py-2 px-4 mb-5 rounded-lg hover:bg-sky-900 transition duration-300 w-full text-lg font-bold"
                    type="submit">Aplicar cambios
            </button>
        </div>
        <%
        } else {
        %>
        <div class="mt-6">
            <button class="bg-cyan-950 text-white py-2 px-4 rounded-lg hover:bg-cyan-900 transition duration-300 w-full text-lg font-bold"
                    type="submit">Crear Proyecto
            </button>
        </div>
        <%
            }
        %>
    </form>
    <%if (isModifying) {
        %>
        <button data-modal-target="delete-proyect" data-modal-toggle="delete-proyect"
                class="bg-red-500 text-white py-2 px-4 rounded-lg hover:bg-red-600 transition duration-300 w-full text-lg font-bold">
            Eliminar Proyecto
        </button>
        <div id="delete-proyect" tabindex="-1" aria-hidden="true"
             class="hidden overflow-y-auto overflow-x-hidden fixed top-0 right-0 left-0 z-50 justify-center items-center w-full md:inset-0 h-[calc(100%-1rem)] max-h-full">
            <div class="relative p-4 w-full max-w-md max-h-full">
                <div class="relative bg-white rounded-lg shadow dark:bg-indigo-950">
                    <div class="flex items-center justify-between p-4 md:p-5 border-b rounded-t dark:border-gray-600">
                        <h3 class="text-xl font-semibold text-gray-900 dark:text-white">
                            ¿Deseas eliminar este proyecto de forma permanente?
                        </h3>
                        <button type="button"
                                class="end-2.5 text-gray-400 bg-transparent hover:bg-gray-200 hover:text-gray-900 rounded-lg text-sm w-8 h-8 ms-auto inline-flex justify-center items-center dark:hover:bg-gray-600 dark:hover:text-white"
                                data-modal-hide="change-name">
                            <svg class="w-3 h-3" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
                                 viewBox="0 0 14 14">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round"
                                      stroke-width="2"
                                      d="m1 1 6 6m0 0 6 6M7 7l6-6M7 7l-6 6"></path>
                            </svg>
                            <span class="sr-only">Close modal</span>
                        </button>
                    </div>
                    <div class="p-4 md:p-5">
                        <form action="${pageContext.request.contextPath}/modify-proyect-servlet" method="post"
                              class="mt-4 max-w-3xl mx-auto">
                            <input type="hidden" name="username"
                                   value="<%out.print(session.getAttribute("username"));%>">
                            <input type="hidden" name="confirmationForDelete" value="<%out.print(true);%>">
                            <input type="hidden" name="codigo_proyecto"
                                   value="<%out.print(proyectoParaModificar.getCodigo());%>">
                            <button type="submit"
                                    class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 py-2.5 mr-1 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800">
                                Eliminar Proyecto
                            </button>
                            <button type="button"
                                    class="w-full text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm px-5 text-center dark:bg-sky-900 dark:hover:bg-sky-950 dark:focus:ring-blue-800"
                                    data-modal-hide="delete-proyect">Cancelar
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
            <%
    }
            %>
</section>

<jsp:include page="../Scripts/FooterScript.jsp"/>
</body>
</html>
<%
    } else request.getServletContext().getRequestDispatcher("/Pages/Index.jsp").forward(request, response);

%>