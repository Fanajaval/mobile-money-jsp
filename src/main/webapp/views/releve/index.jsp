<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>Relevé PDF</title>

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/bootstrap-icons.css">

<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

<link rel="stylesheet"
href="${pageContext.request.contextPath}/assets/css/releve.css">

</head>

<body>

<jsp:include page="../../includes/sidebar.jsp"/>

<div class="main-content">

    <div class="container-fluid">

        <div class="releve-header">

		    <div>
		
		        <h2 class="releve-title">
		
		            <i class="bi bi-file-earmark-pdf-fill"></i>
		
		            Génération Relevé PDF
		
		        </h2>
		
		        <p class="releve-subtitle">
		
		            Relevé mensuel des opérations d'un client
		
		        </p>
		
		    </div>
		
		    <div class="releve-badge">
		
		        <i class="bi bi-shield-check"></i>
		
		        PDF Sécurisé
		
		    </div>
		
		</div>

        <div class="releve-card">

            <div class="card-body">

                <form action="${pageContext.request.contextPath}/releve"
				      method="post"
				      target="_blank"
				      class="releve-form">

                    <!-- CLIENT -->
                    <div class="mb-3">

                        <label class="form-label">
                            Client
                        </label>

                        <select name="numtel"
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

                    <!-- MOIS -->
                    <div class="mb-3">

                        <label class="form-label">
                            Mois
                        </label>

                        <input type="month"
                               name="mois"
                               class="form-control"
                               required>

                    </div>

                    <button class="btn btn-danger">

                        <i class="bi bi-file-earmark-pdf"></i>

                        Générer PDF

                    </button>

                </form>

            </div>

        </div>

    </div>

</div>

</body>
</html>