<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Gestion Clients</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap-icons.css">
<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/client.css">

</head>

<body>

<jsp:include page="../../includes/sidebar.jsp"/>

<div class="main-content">

    <!-- HEADER -->
    <div class="client-header">

        <div>
            <h2 class="client-title">
                <i class="bi bi-people-fill"></i>
                Gestion Clients
            </h2>

            <p class="client-subtitle">
                Gestion moderne des clients
            </p>
        </div>

        <span class="client-count">
            <i class="bi bi-person-check-fill"></i>
            ${listeClients.size()} Clients
        </span>

    </div>

    <!-- TOASTS -->
    <div class="toast-container position-fixed top-0 end-0 p-3" style="z-index:9999;">

        <c:if test="${param.success == 'added'}">
            <div class="toast text-bg-success border-0 show">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-check-circle-fill"></i>
                        Client ajouté avec succès
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.success == 'updated'}">
            <div class="toast text-bg-warning border-0 show">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-pencil-square"></i>
                        Client modifié avec succès
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.success == 'deleted'}">
            <div class="toast text-bg-danger border-0 show">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-trash-fill"></i>
                        Client supprimé avec succès
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.error == 'exists'}">
            <div class="toast text-bg-dark border-0 show">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-exclamation-triangle-fill"></i>
                        Ce numéro existe déjà
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </c:if>

        <c:if test="${param.error == 'validation'}">
            <div class="toast text-bg-secondary border-0 show">
                <div class="d-flex">
                    <div class="toast-body">
                        <i class="bi bi-shield-exclamation"></i>
                        Données invalides
                    </div>
                    <button type="button" class="btn-close btn-close-white me-2 m-auto" data-bs-dismiss="toast"></button>
                </div>
            </div>
        </c:if>
        
        <c:if test="${param.success == 'allDeleted'}">

		    <div class="alert alert-danger alert-dismissible fade show shadow-sm">
		
		        <i class="bi bi-trash3-fill"></i>
		
		        Tous les clients ont été supprimés.
		
		        <button type="button"
		                class="btn-close"
		                data-bs-dismiss="alert"></button>
		
		    </div>
		
		</c:if>

    </div>

    <!-- FORMULAIRE -->
    <div class="table-container mb-4">

        <div class="d-flex justify-content-between align-items-center mb-4">
            <h5 class="mb-0">
                <i class="bi bi-person-plus-fill"></i>
                Ajouter Client
            </h5>
        </div>

        <form action="${pageContext.request.contextPath}/client" method="post">

            <div class="row">

                <!-- NUMERO -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Numéro téléphone</label>

                    <input type="text"
                           name="numtel"
                           class="form-control"
                           placeholder="03XXXXXXXX"
                           maxlength="10"
                           pattern="03[0-9]{8}"
                           inputmode="numeric"
                           oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                           required>
                </div>

                <!-- NOM -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Nom complet</label>

                    <input type="text"
                           name="nom"
                           class="form-control"
                           placeholder="Nom complet"
                           required>
                </div>

                <!-- SEXE -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Sexe</label>

                    <select name="sexe" class="form-select">
                        <option value="Masculin">Masculin</option>
                        <option value="Féminin">Féminin</option>
                    </select>
                </div>

                <!-- AGE -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Age</label>

                    <input type="number"
                           name="age"
                           class="form-control"
                           placeholder="Age"
                           min="1"
                           required>
                </div>

                <!-- SOLDE -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Solde</label>

                    <input type="number"
                           name="solde"
                           class="form-control fw-bold text-success"
                           placeholder="0 Ar"
                           min="0"
                           required>
                </div>

                <!-- EMAIL -->
                <div class="col-md-4 mb-3">
                    <label class="form-label">Email</label>

                    <input type="email"
                           name="mail"
                           class="form-control"
                           placeholder="exemple@gmail.com"
                           required>
                </div>

            </div>

            <div class="d-flex gap-2">

                <button class="btn btn-primary">
                    <i class="bi bi-plus-circle"></i>
                    Ajouter
                </button>

                <button type="reset" class="btn btn-secondary">
                    <i class="bi bi-eraser-fill"></i>
                    Vider
                </button>

            </div>

        </form>

    </div>

    <!-- RECHERCHE -->
	<div class="table-container mb-4">
	
	    <div class="d-flex align-items-center gap-2">
	
	        <!-- SEARCH -->
	        <div class="search-box" style="width:300px;">
	
	            <i class="bi bi-search"></i>
	
	            <input type="text"
	                   id="searchInput"
	                   class="form-control"
	                   placeholder="Téléphone, nom ou email">
	
	        </div>
	
	        <!-- REFRESH -->
	        <a href="${pageContext.request.contextPath}/client"
	           class="btn btn-dark">
	
	            <i class="bi bi-arrow-clockwise"></i>
	
	            Rafraîchir
	
	        </a>
	        
	        <a href="${pageContext.request.contextPath}/client?action=deleteAll"
			   class="btn btn-danger"
			
			   onclick="return confirm('Supprimer tous les clients ?')">
			
			    <i class="bi bi-trash3-fill"></i>
			
			    Supprimer Tout
			
			</a>
	
	    </div>
	
	</div>

    <!-- TABLE -->
    <div class="table-container">

        <div class="table-responsive">

            <table class="table table-hover align-middle client-table" id="clientTable">

                <thead>

                    <tr>
                        <th>Numéro</th>
                        <th>Nom</th>
                        <th>Sexe</th>
                        <th>Age</th>
                        <th>Solde</th>
                        <th>Email</th>
                        <th width="180">Actions</th>
                    </tr>

                </thead>

                <tbody>

                    <!-- EMPTY -->
                    <c:if test="${empty listeClients}">
                        <tr>
                            <td colspan="7">
    							<div class="empty-box">

                                	<i class="bi bi-people" style="font-size:70px;color:gray;"></i>

                                	<h5 class="mt-3 text-muted">
                                    	Aucun client trouvé
                                	</h5>
								</div>
                            </td>
                        </tr>
                    </c:if>

                    <!-- LISTE -->
                    <c:forEach items="${listeClients}" var="c">

                        <tr>

                            <form action="${pageContext.request.contextPath}/client" method="post">

                                <input type="hidden" name="action" value="update">

                                <!-- NUMERO -->
                                <td>
                                    <input type="text"
                                           name="numtel"
                                           value="${c.numtel}"
                                           class="form-control edit-field"
                                           maxlength="10"
                                           pattern="03[0-9]{8}"
                                           inputmode="numeric"
                                           oninput="this.value=this.value.replace(/[^0-9]/g,'')"
                                           readonly>
                                </td>

                                <!-- NOM -->
                                <td>
                                    <input type="text"
                                           name="nom"
                                           value="${c.nom}"
                                           class="form-control edit-field"
                                           disabled>
                                </td>

                                <!-- SEXE -->
                                <td>
                                    <select name="sexe" class="form-select edit-field" disabled>

                                        <option value="Masculin" ${c.sexe=='Masculin'?'selected':''}>
                                            Masculin
                                        </option>

                                        <option value="Féminin" ${c.sexe=='Féminin'?'selected':''}>
                                            Féminin
                                        </option>

                                    </select>
                                </td>

                                <!-- AGE -->
                                <td>
                                    <input type="number"
                                           name="age"
                                           value="${c.age}"
                                           class="form-control edit-field"
                                           min="1"
                                           disabled>
                                </td>

                                <!-- SOLDE -->
                                <td>
                                    <input type="number"
                                           name="solde"
                                           value="${c.solde}"
                                           class="form-control edit-field fw-bold text-success"
                                           min="0"
                                           disabled>
                                </td>

                                <!-- EMAIL -->
                                <td>
                                    <input type="email"
                                           name="mail"
                                           value="${c.mail}"
                                           class="form-control edit-field"
                                           disabled>
                                </td>

                                <!-- ACTIONS -->
                                <td>

                                    <!-- NORMAL -->
                                    <div class="normal-actions">

                                        <button type="button"
                                                class="btn btn-warning btn-sm"
                                                onclick="enableEdit(this)"
                                                title="Modifier">

                                            <i class="bi bi-pencil-square"></i>

                                        </button>

                                        <a href="${pageContext.request.contextPath}/client?action=delete&numtel=${c.numtel}"
                                           class="btn btn-danger btn-sm"
                                           onclick="return confirm('Supprimer ce client ?')"
                                           title="Supprimer">

                                            <i class="bi bi-trash"></i>

                                        </a>

                                    </div>

                                    <!-- EDIT -->
                                    <div class="edit-actions d-none">

                                        <button class="btn btn-success btn-sm" title="Valider">
                                            <i class="bi bi-check-circle"></i>
                                        </button>

                                        <button type="button"
                                                class="btn btn-secondary btn-sm"
                                                onclick="cancelEdit()"
                                                title="Annuler">

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

/* RECHERCHE */
const searchInput=document.getElementById("searchInput");

searchInput.addEventListener("keyup",function(){

    let filter=searchInput.value.toLowerCase();

    let rows=document.querySelectorAll("#clientTable tbody tr");

    rows.forEach(function(row){

        let numtel=row.querySelector('input[name="numtel"]')?.value.toLowerCase()||"";
        let nom=row.querySelector('input[name="nom"]')?.value.toLowerCase()||"";
        let email=row.querySelector('input[name="mail"]')?.value.toLowerCase()||"";

        if(numtel.includes(filter)||nom.includes(filter)||email.includes(filter)){

            row.style.display="";

        }else{

            row.style.display="none";
        }

    });

});

/* EDIT */
function enableEdit(button){

    let row=button.closest("tr");

    row.querySelectorAll(".edit-field").forEach(function(field){

        if(field.name!=="numtel"){

            field.disabled=false;
        }

    });

    row.querySelector(".normal-actions").classList.add("d-none");
    row.querySelector(".edit-actions").classList.remove("d-none");
}

/* CANCEL */
function cancelEdit(){
    location.reload();
}

/* AUTO HIDE TOASTS */
setTimeout(()=>{

    document.querySelectorAll(".toast").forEach(toast=>{
        toast.classList.remove("show");
    });

},3000);

</script>

</body>
</html>