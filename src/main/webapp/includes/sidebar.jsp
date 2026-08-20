<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="sidebar">

    <!-- LOGO -->
    <div class="logo">

        <i class="bi bi-phone-fill"></i>

        <span>
            MobileMoney
        </span>

    </div>

    <!-- MENU -->
    <ul class="nav flex-column mt-4">

        <!-- DASHBOARD -->
        <li class="nav-item">

            <a href="${pageContext.request.contextPath}/dashboard"
   				class="nav-link active">

                <i class="bi bi-speedometer2"></i>

                <span>
                    Dashboard
                </span>

            </a>

        </li>

        <!-- CLIENT -->
        <li class="nav-item">

            <a href="${pageContext.request.contextPath}/client"
               class="nav-link active">

                <i class="bi bi-people-fill"></i>

                <span>
                    Clients
                </span>

            </a>

        </li>

        <!-- ENVOI -->
        <li class="nav-item">

            <a href="${pageContext.request.contextPath}/envoi"
               class="nav-link active">

                <i class="bi bi-send-fill"></i>

                <span>
                    Envoi d'Argent
                </span>

            </a>

        </li>

        <!-- RETRAIT -->
        <li class="nav-item">

            <a href="${pageContext.request.contextPath}/retrait"
               class="nav-link active">

                <i class="bi bi-cash-stack"></i>

                <span>
                    Retraits
                </span>

            </a>

        </li>

        <!-- FRAIS -->
        <!-- FRAIS ENVOI -->
		<li class="nav-item">
		
		    <a href="${pageContext.request.contextPath}/frais-envoi"
		       class="nav-link active">
		
		        <i class="bi bi-receipt-cutoff"></i>
		
		        <span>
		            Frais Envoi
		        </span>
		
		    </a>
		
		</li>
		
		<!-- FRAIS RETRAIT -->
		<li class="nav-item">
		
		    <a href="${pageContext.request.contextPath}/frais-recep"
		       class="nav-link active">
		
		        <i class="bi bi-cash-coin"></i>
		
		        <span>
		            Frais Retrait
		        </span>
		
		    </a>
		
		</li>
		
		<!-- RELEVE PDF -->
		<li class="nav-item">
		
		    <a href="${pageContext.request.contextPath}/releve"
		       class="nav-link active">
		
		        <i class="bi bi-file-earmark-pdf-fill"></i>
		
		        <span>
		            Relevé PDF
		        </span>
		
		    </a>
		
		</li>

    </ul>

</div>