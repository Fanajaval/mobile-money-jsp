<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core"
prefix="c"%>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Retrait d'Argent</title>

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap-icons.css">

<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/retrait.css">

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

                    Retrait effectué avec succès.

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

                    Retrait supprimé avec succès.

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

                    Retrait modifié avec succès.

                </div>

                <button type="button"
                        class="btn-close btn-close-white me-2 m-auto"
                        data-bs-dismiss="toast">
                </button>

            </div>

        </div>

    </c:if>

    <!-- ERROR -->
    <c:if test="${param.error == 'invalid'}">

        <div class="toast text-bg-danger border-0 show">

            <div class="d-flex">

                <div class="toast-body">

                    <i class="bi bi-exclamation-triangle-fill"></i>

                    Code de référence invalide.

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
<div class="retrait-header">

    <div>

        <h2 class="retrait-title">

            <i class="bi bi-cash-stack"></i>

            Retrait d'Argent

        </h2>

        <p class="retrait-subtitle">

            Gestion des retraits Mobile Money

        </p>

    </div>

    <div class="retrait-badge">

        <i class="bi bi-clock-history"></i>

        ${listeRetraits.size()} Retraits

    </div>

</div>

<!-- FORMULAIRE -->
<div class="table-container mb-4">

    <div class="d-flex justify-content-between align-items-center mb-4">

        <h5 class="mb-0">

            <i class="bi bi-wallet2"></i>

            Nouveau Retrait

        </h5>

    </div>

    <form action="${pageContext.request.contextPath}/retrait"
          method="post">

        <div class="row">

            <!-- REFERENCE -->
            <div class="col-md-6 mb-3">

                <label class="form-label">

                    Code Référence

                </label>

                <input type="text"
                       name="referenceCode"
                       class="form-control"
                       placeholder="MM-XXXXXX"
                       required>

            </div>

        </div>

        <div class="d-flex gap-2">

            <button class="btn btn-primary">

                <i class="bi bi-cash"></i>

                Retirer

            </button>

            <button type="reset"
                    class="btn btn-secondary">

                <i class="bi bi-eraser-fill"></i>

                Vider

            </button>

        </div>

    </form>

</div>

<!-- RECHERCHE -->
<div class="table-container mb-4">

    <div class="d-flex align-items-center gap-2 flex-wrap">

        <!-- RECHERCHE DATE -->
        <form id="searchDateForm"
              class="d-flex align-items-center gap-2">

            <input type="date"
                   id="dateSearch"
                   class="form-control">

            <button class="btn btn-primary">

                <i class="bi bi-calendar-search"></i>

                Rechercher

            </button>

        </form>

        <!-- RECHERCHE TEXTE -->
        <div class="search-box"
             style="width:300px;">

            <i class="bi bi-search"></i>

            <input type="text"
                   id="searchInput"
                   class="form-control"
                   placeholder="Référence ou numéro">

        </div>

        <!-- REFRESH -->
        <a href="${pageContext.request.contextPath}/retrait"
           class="btn btn-dark">

            <i class="bi bi-arrow-clockwise"></i>

            Rafraîchir

        </a>

    </div>

</div>

<!-- TABLE -->
<div class="table-container">

    <div class="table-responsive">

        <table class="table table-hover align-middle retrait-table"
       		id="retraitTable">

            <thead class="table-dark">

                <tr>

                    <th>Référence</th>

                    <th>Client</th>

                    <th>Montant</th>

                    <th>Frais</th>

                    <th>Date</th>

                    <th width="180">

                        Actions

                    </th>

                </tr>

            </thead>

            <tbody id="tableBody">

                <!-- VIDE -->
                <c:if test="${empty listeRetraits}">

                    <tr>

                        <td colspan="6">

						    <div class="empty-box">
						
						        <i class="bi bi-wallet2"></i>
						
						        <h5>
						
						            Aucun retrait trouvé
						
						        </h5>
						
						    </div>
						
						</td>

                    </tr>

                </c:if>

                <!-- LISTE -->
                <c:forEach items="${listeRetraits}" var="r">

                    <tr>

                        <form action="${pageContext.request.contextPath}/retrait"
                              method="post">

                            <input type="hidden"
                                   name="action"
                                   value="update">

                            <input type="hidden"
                                   name="idRet"
                                   value="${r.idRet}">

                            <!-- REF -->
                            <td>

                                <span class="badge bg-dark">

                                    ${r.referenceCode}

                                </span>

                            </td>

                            <!-- CLIENT -->
                            <td>

                                <div class="fw-bold">

                                    ${r.nomClient}

                                </div>

                                <small class="text-muted">

                                    ${r.numClient}

                                </small>

                            </td>

                            <!-- MONTANT -->
                            <td>

                                <span class="fw-bold text-success">

                                    ${r.montantRetrait} Ar

                                </span>

                            </td>

                            <!-- FRAIS -->
                            <td>

                                <input type="number"
                                       name="fraisRetrait"
                                       value="${r.fraisRetrait}"
                                       class="form-control edit-field"
                                       min="0"
                                       disabled>

                            </td>

                            <!-- DATE -->
                            <td>

                                ${r.dateRetrait}

                            </td>

                            <!-- ACTIONS -->
                            <td>

                                <!-- NORMAL -->
                                <div class="normal-actions">

                                    <button type="button"
                                            class="btn btn-warning btn-sm"
                                            onclick="enableEdit(this)">

                                        <i class="bi bi-pencil-square"></i>

                                    </button>

                                    <a href="${pageContext.request.contextPath}/retrait?action=delete&idRet=${r.idRet}"
                                       class="btn btn-danger btn-sm"
                                       onclick="return confirm('Supprimer ce retrait ?')">

                                        <i class="bi bi-trash-fill"></i>

                                    </a>

                                </div>

                                <!-- EDIT -->
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
   RECHERCHE DYNAMIQUE
========================= */

const searchInput =
document.getElementById("searchInput");

searchInput.addEventListener(
"keyup",
function(){

    let filter =
    this.value.toLowerCase();

    let rows =
    document.querySelectorAll(
    "#retraitTable tbody tr");

    rows.forEach(function(row){

        let text =
        row.innerText.toLowerCase();

        row.style.display =
        text.includes(filter)
        ? ""
        : "none";

    });

});

/* =========================
   MODIFICATION INLINE
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
   CANCEL EDIT
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

/* =========================
   AJAX RECHERCHE DATE
========================= */

const searchDateForm =
document.getElementById(
"searchDateForm");

searchDateForm.addEventListener(
"submit",
function(e){

    e.preventDefault();

    let date =
    document.getElementById(
    "dateSearch").value;

    fetch(
    "${pageContext.request.contextPath}/retrait?dateSearch="
    + date)

    .then(response => response.text())

    .then(html => {

        let parser =
        new DOMParser();

        let doc =
        parser.parseFromString(
        html,
        "text/html");

        let newBody =
        doc.getElementById(
        "tableBody");

        document.getElementById(
        "tableBody").innerHTML =
        newBody.innerHTML;

    });

});

</script>

</body>
</html>