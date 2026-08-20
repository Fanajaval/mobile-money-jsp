<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Frais Retrait</title>

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

    <!-- SUCCESS AJOUT -->
    <c:if test="${param.success == 'added'}">

        <div class="toast text-bg-success border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-check-circle-fill"></i>

                    Frais retrait ajouté avec succès.

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

                    Frais retrait modifié avec succès.

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

                    Frais retrait supprimé avec succès.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- ERROR -->
    <c:if test="${not empty param.error}">

        <div class="toast text-bg-danger border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-exclamation-triangle-fill"></i>

                    Erreur lors de l'opération.

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

            <i class="bi bi-cash-coin"></i>

            Frais Retrait

        </h2>

        <p class="text-muted">

            Gestion des frais de retrait

        </p>

    </div>

    <span class="badge bg-danger p-3">

        ${totalRegles} Configurations

    </span>

</div>

<!-- FORMULAIRE -->
<div class="table-container mb-4">

    <h5 class="mb-4">

        <i class="bi bi-plus-circle"></i>

        Nouveau frais retrait

    </h5>

    <form action="${pageContext.request.contextPath}/frais-recep"
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

                    Frais Retrait

                </label>

                <input type="number"
                       step="0.01"
                       min="0"
                       name="fraisRec"
                       class="form-control"
                       required>

            </div>

        </div>

        <button class="btn btn-danger">

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

                <!-- VIDE -->
                <c:if test="${empty listeFrais}">

                    <tr>

                        <td colspan="5"
                            class="text-center p-5">

                            <i class="bi bi-cash-coin"
                               style="font-size:70px;color:gray;">
                            </i>

                            <h5 class="mt-3 text-muted">

                                Aucun frais retrait trouvé

                            </h5>

                        </td>

                    </tr>

                </c:if>

                <c:forEach items="${listeFrais}" var="f">

                    <tr>

                        <form action="${pageContext.request.contextPath}/frais-recep"
                              method="post">

                            <input type="hidden"
                                   name="action"
                                   value="update">

                            <input type="hidden"
                                   name="idRec"
                                   value="${f.idRec}">

                            <!-- ID -->
                            <td>

                                ${f.idRec}

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
                                       name="fraisRec"
                                       value="${f.fraisRec}"
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

                                    <a href="${pageContext.request.contextPath}/frais-recep?action=delete&idRec=${f.idRec}"
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
   AUTO HIDE TOAST
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