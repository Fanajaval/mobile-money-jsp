<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Frais Envoi</title>

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap-icons.css">

<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

</head>

<body>

<!-- SIDEBAR -->
<jsp:include page="../../includes/sidebar.jsp"/>

<div class="main-content">

<!-- TOASTS -->
<div class="toast-container position-fixed top-0 end-0 p-3">

    <!-- SUCCESS ADD -->
    <c:if test="${param.success == 'added'}">

        <div class="toast text-bg-success border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-check-circle-fill"></i>

                    Frais ajouté avec succès.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- SUCCESS UPDATE -->
    <c:if test="${param.success == 'updated'}">

        <div class="toast text-bg-warning border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-pencil-square"></i>

                    Frais modifié avec succès.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- SUCCESS DELETE -->
    <c:if test="${param.success == 'deleted'}">

        <div class="toast text-bg-danger border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-trash-fill"></i>

                    Frais supprimé avec succès.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- VALIDATION -->
    <c:if test="${param.error == 'validation'}">

        <div class="toast text-bg-danger border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-exclamation-triangle-fill"></i>

                    Valeurs invalides.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- SERVER -->
    <c:if test="${param.error == 'server'}">

        <div class="toast text-bg-dark border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-server"></i>

                    Erreur serveur.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

</div>

<!-- HEADER -->
<div class="d-flex justify-content-between align-items-center mb-4">

    <div>

        <h2 class="fw-bold">

            <i class="bi bi-cash-stack"></i>

            Frais Envoi

        </h2>

        <p class="text-muted">

            Gestion des frais d'envoi

        </p>

    </div>

    <span class="badge bg-primary p-3">

        ${listeFrais.size()} Configurations

    </span>

</div>

<!-- FORM -->
<div class="table-container mb-4">

    <h5 class="mb-4">

        <i class="bi bi-plus-circle"></i>

        Nouveau frais

    </h5>

    <form action="${pageContext.request.contextPath}/frais-envoi"
          method="post">

        <div class="row">

            <!-- MIN -->
            <div class="col-md-3 mb-3">

                <label class="form-label">

                    Montant Min

                </label>

                <input type="number"
                       step="0.01"
                       min="0"
                       name="montant1"
                       class="form-control"
                       required>

            </div>

            <!-- MAX -->
            <div class="col-md-3 mb-3">

                <label class="form-label">

                    Montant Max

                </label>

                <input type="number"
                       step="0.01"
                       min="0"
                       name="montant2"
                       class="form-control"
                       required>

            </div>

            <!-- FRAIS -->
            <div class="col-md-3 mb-3">

                <label class="form-label">

                    Frais Envoi

                </label>

                <input type="number"
                       step="0.01"
                       min="0"
                       name="fraisEnv"
                       class="form-control"
                       required>

            </div>

        </div>

        <button class="btn btn-primary">

            <i class="bi bi-save"></i>

            Ajouter

        </button>

    </form>

</div>

<!-- TABLE -->
<div class="table-container">

    <div class="table-responsive">

        <table class="table table-hover align-middle">

            <thead class="table-dark">

                <tr>

                    <th>ID</th>

                    <th>Montant Min</th>

                    <th>Montant Max</th>

                    <th>Frais</th>

                    <th width="220">

                        Actions

                    </th>

                </tr>

            </thead>

            <tbody>

                <c:forEach items="${listeFrais}" var="f">

                    <tr>

                        <form action="${pageContext.request.contextPath}/frais-envoi"
                              method="post">

                            <input type="hidden"
                                   name="action"
                                   value="update">

                            <input type="hidden"
                                   name="idEnv"
                                   value="${f.idEnv}">

                            <!-- ID -->
                            <td>

                                ${f.idEnv}

                            </td>

                            <!-- MIN -->
                            <td>

                                <input type="number"
                                       step="0.01"
                                       min="0"
                                       name="montant1"
                                       value="${f.montant1}"
                                       class="form-control edit-field"
                                       disabled>

                            </td>

                            <!-- MAX -->
                            <td>

                                <input type="number"
                                       step="0.01"
                                       min="0"
                                       name="montant2"
                                       value="${f.montant2}"
                                       class="form-control edit-field"
                                       disabled>

                            </td>

                            <!-- FRAIS -->
                            <td>

                                <input type="number"
                                       step="0.01"
                                       min="0"
                                       name="fraisEnv"
                                       value="${f.fraisEnv}"
                                       class="form-control edit-field"
                                       disabled>

                            </td>

                            <!-- ACTIONS -->
                            <td>

                                <div class="normal-actions">

                                    <button type="button"
                                            class="btn btn-warning btn-sm"
                                            onclick="enableEdit(this)">

                                        <i class="bi bi-pencil-square"></i>

                                    </button>

                                    <a href="${pageContext.request.contextPath}/frais-envoi?action=delete&idEnv=${f.idEnv}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Supprimer ce frais ?')">

                                        <i class="bi bi-trash-fill"></i>

                                    </a>

                                </div>

                                <div class="edit-actions d-none">

                                    <button class="btn btn-success btn-sm">

                                        <i class="bi bi-check-circle"></i>

                                    </button>

                                    <button type="button"
                                            class="btn btn-secondary btn-sm"
                                            onclick="cancelEdit()">

                                        <i class="bi bi-x-circle"></i>

                                    </button>

                                </div>

                            </td>

                        </form>

                    </tr>

                </c:forEach>

            </tbody>

        </table>

    </div>

</div>

</div>

<script src="${pageContext.request.contextPath}/assets/js/bootstrap.bundle.min.js"></script>

<script>

/* =========================
   EDIT INLINE
========================= */

function enableEdit(button){

    let row =
    button.closest("tr");

    row.querySelectorAll(".edit-field")
    .forEach(function(field){

        field.disabled = false;

    });

    row.querySelector(".normal-actions")
    .classList.add("d-none");

    row.querySelector(".edit-actions")
    .classList.remove("d-none");
}

/* =========================
   CANCEL
========================= */

function cancelEdit(){

    location.reload();
}

/* =========================
   TOAST AUTO HIDE
========================= */

setTimeout(() => {

    document
    .querySelectorAll(".toast")
    .forEach(toastEl => {

        const toast =
        bootstrap.Toast
        .getOrCreateInstance(
        toastEl);

        toast.hide();

    });

}, 3000);

</script>

</body>
</html>