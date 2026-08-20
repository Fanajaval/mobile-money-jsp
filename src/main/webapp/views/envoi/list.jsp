<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Envoi d'Argent</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-icons.css">
<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/envoi.css">

</head>

<body>

<!-- SIDEBAR -->
<jsp:include page="../../includes/sidebar.jsp"/>

<div class="main-content">

    <!-- TOAST -->
    <div class="toast-container position-fixed top-0 end-0 p-3">

        <!-- SUCCESS AJOUT -->
        <c:if test="${param.success == 'added'}">
            <div class="toast align-items-center text-bg-success border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-check-circle-fill"></i>
                        Envoi effectué avec succès.
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
            <div class="toast align-items-center text-bg-danger border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-trash-fill"></i>
                        Envoi supprimé et soldes restaurés.
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
		
		    <div class="toast align-items-center text-bg-warning border-0 show shadow">
		
		        <div class="d-flex">
		
		            <div class="toast-body">
		
		                <i class="bi bi-pencil-square"></i>
		
		                Envoi modifié avec succès.
		
		            </div>
		
		            <button type="button"
		                    class="btn-close btn-close-white me-2 m-auto"
		                    data-bs-dismiss="toast">
		            </button>
		
		        </div>
		
		    </div>
		
		</c:if>

        <!-- ERREUR SOLDE -->
        <c:if test="${param.error == 'solde'}">
            <div class="toast align-items-center text-bg-danger border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-exclamation-triangle-fill"></i>
                        Solde insuffisant.
                    </div>

                    <button type="button"
                            class="btn-close btn-close-white me-2 m-auto"
                            data-bs-dismiss="toast">
                    </button>
                </div>
            </div>
        </c:if>

        <!-- ERREUR MONTANT -->
        <c:if test="${param.error == 'montant'}">
            <div class="toast align-items-center text-bg-warning border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-exclamation-circle-fill"></i>
                        Montant invalide.
                    </div>

                    <button type="button"
                            class="btn-close btn-close-white me-2 m-auto"
                            data-bs-dismiss="toast">
                    </button>
                </div>
            </div>
        </c:if>

        <!-- ERREUR SELF -->
        <c:if test="${param.error == 'self'}">
            <div class="toast align-items-center text-bg-warning border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-exclamation-triangle-fill"></i>
                        Impossible d'envoyer à vous-même.
                    </div>

                    <button type="button"
                            class="btn-close btn-close-white me-2 m-auto"
                            data-bs-dismiss="toast">
                    </button>
                </div>
            </div>
        </c:if>

        <!-- ERREUR SUPPRESSION -->
        <c:if test="${param.error == 'delete'}">
            <div class="toast align-items-center text-bg-danger border-0 show shadow">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-x-circle-fill"></i>
                        Impossible de supprimer cet envoi.
                    </div>

                    <button type="button"
                            class="btn-close btn-close-white me-2 m-auto"
                            data-bs-dismiss="toast">
                    </button>
                </div>
            </div>
        </c:if>
        
        <!-- ERREUR UPDATE -->
		<c:if test="${param.error == 'update'}">
		
		    <div class="toast align-items-center text-bg-danger border-0 show shadow">
		
		        <div class="d-flex">
		
		            <div class="toast-body">
		
		                <i class="bi bi-x-circle-fill"></i>
		
		                Impossible de modifier cet envoi.
		
		            </div>
		
		            <button type="button"
		                    class="btn-close btn-close-white me-2 m-auto"
		                    data-bs-dismiss="toast">
		            </button>
		
		        </div>
		
		    </div>
		
		</c:if>

        <!-- ERREUR SERVEUR -->
        <c:if test="${param.error == 'server'}">
            <div class="toast align-items-center text-bg-danger border-0 show shadow">
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
    <div class="envoi-header">

	    <div>
	
	        <h2 class="envoi-title">
	            <i class="bi bi-send-fill"></i>
	            Envoi d'Argent
	        </h2>
	
	        <p class="envoi-subtitle">
	            Gestion des transferts Mobile Money
	        </p>
	
	    </div>
	
	    <div class="envoi-badge">
	
	        <i class="bi bi-clock-history"></i>
	        ${listeEnvois.size()} Transactions
	
	    </div>
	
	</div>

	<!-- STATS -->
	
	<!-- FORMULAIRE -->
	<div class="table-container mb-4">

        <div class="d-flex justify-content-between align-items-center mb-4">

            <h5 class="mb-0">
                <i class="bi bi-cash-coin"></i>
                Nouveau Transfert
            </h5>

        </div>

        <form action="${pageContext.request.contextPath}/envoi"
              method="post">

            <div class="row">

                <!-- ENVOYEUR -->
                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Numéro Envoyeur
                    </label>

                    <select name="numEnvoyeur"
                            id="numEnvoyeur"
                            class="form-select"
                            required>

                        <option value="">
                            Choisir un client
                        </option>

                        <c:forEach items="${listeClients}" var="c">
                            <option value="${c.numtel}">
                                ${c.nom} - ${c.numtel}
                            </option>
                        </c:forEach>

                    </select>

                </div>

                <!-- RECEPTEUR -->
                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Numéro Récepteur
                    </label>

                    <select name="numRecepteur"
                            id="numRecepteur"
                            class="form-select"
                            required>

                        <option value="">
                            Choisir un client
                        </option>

                        <c:forEach items="${listeClients}" var="c">
                            <option value="${c.numtel}">
                                ${c.nom} - ${c.numtel}
                            </option>
                        </c:forEach>

                    </select>

                </div>

                <!-- MONTANT -->
                <div class="col-md-4 mb-3">

                    <label class="form-label">
                        Montant
                    </label>

                    <input type="number"
                           id="montant"
                           name="montant"
                           class="form-control fw-bold text-success"
                           placeholder="0 Ar"
                           min="1"
                           required>

                </div>

                <!-- RAISON -->
                <div class="col-md-6 mb-3">

                    <label class="form-label">
                        Raison du transfert
                    </label>

                    <input type="text"
                           name="raison"
                           class="form-control"
                           placeholder="Ex : Salaire, Aide, Facture..."
                           required>

                </div>

                <!-- FRAIS ENVOI -->
                <div class="col-md-3 mb-3">

                    <label class="form-label">
                        Frais Envoi
                    </label>

                    <input type="text"
                           id="fraisEnvoi"
                           class="form-control"
                           readonly>

                </div>

                <!-- FRAIS RETRAIT -->
                <div class="col-md-3 mb-3">

                    <label class="form-label">
                        Frais Retrait
                    </label>

                    <input type="text"
                           id="fraisRetrait"
                           class="form-control"
                           readonly>

                </div>

            </div>

            <!-- CHECKBOX -->
            <div class="form-check mb-4">

                <input class="form-check-input"
                       type="checkbox"
                       name="payerFraisRetrait"
                       id="payerFraisRetrait">

                <label class="form-check-label"
                       for="payerFraisRetrait">

                    L'envoyeur paie aussi les frais de retrait

                </label>

            </div>

            <!-- RÉSUMÉ TRANSACTION -->
            <div class="alert alert-light border mb-4">

                <div class="row text-center">

                    <div class="col-md-4">
                        <small class="text-muted d-block">
                            Total débité envoyeur
                        </small>

                        <span id="totalDebit"
                              class="fw-bold text-danger">
                            0 Ar
                        </span>
                    </div>

                    <div class="col-md-4">
                        <small class="text-muted d-block">
                            Montant reçu
                        </small>

                        <span id="totalReception"
                              class="fw-bold text-success">
                            0 Ar
                        </span>
                    </div>

                    <div class="col-md-4">
                        <small class="text-muted d-block">
                            Frais total
                        </small>

                        <span id="totalFrais"
                              class="fw-bold text-primary">
                            0 Ar
                        </span>
                    </div>

                </div>

            </div>

            <!-- BOUTONS -->
            <div class="d-flex gap-2">

                <button class="btn btn-primary">
                    <i class="bi bi-send-check-fill"></i>
                    Envoyer
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
	
	    <div class="d-flex justify-content-between align-items-center flex-wrap gap-3">
	
	        <!-- PARTIE GAUCHE -->
	        <div class="d-flex align-items-center gap-2 flex-wrap">
	
	            <!-- RECHERCHE DATE -->
	            <form id="searchForm"
	                  class="d-flex align-items-center gap-2">
	
	                <input type="date"
	                       id="dateSearch"
	                       name="dateSearch"
	                       class="form-control"
	                       value="${param.dateSearch}">
	
	                <button type="submit"
	                        class="btn btn-primary">
	
	                    <i class="bi bi-calendar-search"></i>
	                    Rechercher
	
	                </button>
	
	            </form>
	
	            <!-- RECHERCHE DYNAMIQUE -->
	            <div class="search-box"
	                 style="width:300px;">
	
	                <i class="bi bi-search"></i>
	
	                <input type="text"
	                       id="searchInput"
	                       class="form-control"
	                       placeholder="Référence ou numéro">
	
	            </div>
	
	            <!-- RAFRAICHIR -->
	            <a href="${pageContext.request.contextPath}/envoi"
	               class="btn btn-dark">
	
	                <i class="bi bi-arrow-clockwise"></i>
	                Rafraîchir
	
	            </a>
	
	        </div>
	
	        <!-- PARTIE DROITE : RECETTES -->
	        <div class="text-end">
	
	            <div>
	                <strong>Frais d'envoi total :</strong>
	                ${totalFraisEnvoi} Ar
	            </div>
	
	            <div>
	                <strong>Frais de retrait total :</strong>
	                ${totalFraisRetrait} Ar
	            </div>
	
	            <div>
	                <strong>Recette totale opérateur :</strong>
	                ${recetteTotale} Ar
	            </div>
	
	        </div>
	
	    </div>
	
	</div>
    

    <!-- TABLE -->
    <div class="table-container">

        <div class="table-responsive">

            <table class="table table-hover align-middle"
                   id="envoiTable">

                <thead class="table-dark">

                    <tr>
                        <th>Référence</th>
                        <th>Envoyeur</th>
                        <th>Récepteur</th>
                        <th>Montant</th>
                        <th>Frais</th>
                        <th>Raison</th>
                        <th>Date</th>
                        <th>Actions</th>
                    </tr>

                </thead>

                <tbody id="tableBody">

                    <c:forEach items="${listeEnvois}" var="e">

						<tr>
						
							<form action="${pageContext.request.contextPath}/envoi" method="post">
						
								<input type="hidden" name="action" value="update">
						
								<input type="hidden" name="idEnv" value="${e.idEnv}">
									<td>
									    <span class="badge bg-dark">
									
									        ${e.referenceCode}
									
									    </span>
									
									</td>
									
									<td>
									
									    <div class="fw-bold">
									
									        ${e.nomEnvoyeur}
									
									    </div>
									
									    <small class="text-muted">
									
									        ${e.numEnvoyeur}
									
									    </small>
									
									</td>
									
									<td>
									
									    <div class="fw-bold">
									
									        ${e.nomRecepteur}
									
									    </div>
									
									    <small class="text-muted">
									
									        ${e.numRecepteur}
									
									    </small>
									
									</td>
									
									<td>
									
									    <input type="number"
									       name="montant"
									       value="${e.montant}"
									       class="form-control edit-field fw-bold text-success"
									       min="1"
									       disabled>
									
									</td>
									
									<td>
									
									    ${e.fraisEnvoi} Ar
									
									</td>
									
									<td>
									
									    <input type="text"
									           name="raison"
									           value="${e.raison}"
									           class="form-control edit-field"
									           disabled>
									
									</td>
									
									<td>
									
									    ${e.dateEnvoi}
									
									</td>
									
									<td>
									
									    <!-- NORMAL -->
									    <div class="normal-actions">
									
									        <button type="button"
									                class="btn btn-warning btn-sm"
									                onclick="enableEdit(this)">
									
									            <i class="bi bi-pencil-square"></i>
									
									        </button>
									
									        <a href="${pageContext.request.contextPath}/envoi?action=delete&idEnv=${e.idEnv}"
									           class="btn btn-danger btn-sm"
									           onclick="return confirm('Supprimer cet envoi ?')">
									
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

/* RECHERCHE DYNAMIQUE */

const searchInput =
document.getElementById("searchInput");

searchInput.addEventListener("keyup", function(){

    let filter =
    searchInput.value.toLowerCase();

    let rows =
    document.querySelectorAll("#envoiTable tbody tr");

    rows.forEach(function(row){

        let text =
        row.innerText.toLowerCase();

        row.style.display =
        text.includes(filter)
        ? ""
        : "none";

    });

});

/* CALCUL FRAIS */

const montantInput =
document.getElementById("montant");

const checkboxRetrait =
document.getElementById("payerFraisRetrait");

function calculerFrais(){

    let montant =
    parseFloat(montantInput.value);

    let fraisE =
    document.getElementById("fraisEnvoi");

    let fraisR =
    document.getElementById("fraisRetrait");

    let totalDebit =
    document.getElementById("totalDebit");

    let totalReception =
    document.getElementById("totalReception");

    let totalFrais =
    document.getElementById("totalFrais");

    if(isNaN(montant) || montant <= 0){

        fraisE.value = "";
        fraisR.value = "";

        totalDebit.innerHTML = "0 Ar";
        totalReception.innerHTML = "0 Ar";
        totalFrais.innerHTML = "0 Ar";

        return;
    }

    let fraisEnvoi = 0;
    let fraisRetrait = 0;

    if(montant <= 50000){

        fraisEnvoi = 200;
        fraisRetrait = 100;

    } else if(montant <= 100000){

        fraisEnvoi = 400;
        fraisRetrait = 200;

    } else if(montant <= 200000){

        fraisEnvoi = 800;
        fraisRetrait = 400;

    } else {

        fraisEnvoi = 1500;
        fraisRetrait = 800;
    }

    fraisE.value =
    fraisEnvoi + " Ar";

    fraisR.value =
    fraisRetrait + " Ar";

    let payerRetrait =
    checkboxRetrait.checked;

    let debit =
    montant + fraisEnvoi;

    let reception =
    	montant;

    	if(payerRetrait){

    	    debit += fraisRetrait;

    	    reception =
    	    montant + fraisRetrait;
    	}

    totalDebit.innerHTML =
    debit + " Ar";

    totalReception.innerHTML =
    reception + " Ar";

    totalFrais.innerHTML =
    (fraisEnvoi + fraisRetrait) + " Ar";
}

montantInput.addEventListener(
"input",
calculerFrais);

checkboxRetrait.addEventListener(
"change",
calculerFrais);

/* EDIT */
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

/* CANCEL */
function cancelEdit(){

    location.reload();
}

/* TOAST AUTO HIDE */

setTimeout(() => {

    document
    .querySelectorAll('.toast')
    .forEach(toastEl => {

        const toast =
        bootstrap.Toast
        .getOrCreateInstance(toastEl);

        toast.hide();

    });

}, 3000);

/* MASQUER MEME NUMERO */

const envoyeurSelect =
document.getElementById("numEnvoyeur");

const recepteurSelect =
document.getElementById("numRecepteur");

envoyeurSelect.addEventListener(
"change",
function() {

    let selectedEnvoyeur =
    this.value;

    for(let option of recepteurSelect.options){

        option.hidden = false;
    }

    for(let option of recepteurSelect.options){

        if(option.value === selectedEnvoyeur
           &&
           option.value !== ""){

            option.hidden = true;

            if(recepteurSelect.value
               === selectedEnvoyeur){

                recepteurSelect.value = "";
            }
        }
    }
});

/* =========================
RECHERCHE AJAX PAR DATE
========================= */

const searchForm =
document.getElementById("searchForm");

searchForm.addEventListener(
"submit",
function(e){

 e.preventDefault();

 let dateValue =
 document.getElementById("dateSearch").value;

 fetch(
     "${pageContext.request.contextPath}/envoi?dateSearch="
     + dateValue
     + "&ajax=true"
 )

 .then(response => response.text())

 .then(data => {

     document.getElementById("tableBody").innerHTML =
     data;

 })

 .catch(error => {

     console.log(error);

 });

});

</script>

</body>
</html>