<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Dashboard</title>

<link rel="stylesheet"
href="assets/css/bootstrap.min.css">

<link rel="stylesheet"
href="assets/css/bootstrap-icons.css">

<link rel="stylesheet"
href="assets/css/global.css">

<link rel="stylesheet"
href="assets/css/sidebar.css">

<link rel="stylesheet"
href="assets/css/dashboard.css">

</head>

<body>

<!-- SIDEBAR -->
<jsp:include page="includes/sidebar.jsp"/>

<!-- MAIN CONTENT -->
<div class="main-content">

    <!-- HEADER -->
    <div class="dashboard-header">

        <div>

            <h1 class="dashboard-title">
                Dashboard
            </h1>

            <p class="dashboard-subtitle">
                Gestion complète de votre plateforme Mobile Money
            </p>

        </div>

        <button class="admin-btn">

            <i class="bi bi-person-circle"></i>

            Administrateur

        </button>

    </div>

    <!-- DASHBOARD CARDS -->
    <div class="row g-4">

        <!-- CLIENTS -->
        <div class="col-lg-4 col-md-6">

            <div class="card-dashboard bg-blue">

                <div class="card-overlay"></div>

                <div class="d-flex justify-content-between align-items-center">

                    <div>

                        <h5>Total Clients</h5>

                        <h2>${totalClients}</h2>

                        <div class="card-mini-text">
                            Clients enregistrés
                        </div>

                    </div>

                    <div class="card-icon">

                        <i class="bi bi-people-fill"></i>

                    </div>

                </div>

            </div>

        </div>

        <!-- ENVOIS -->
        <div class="col-lg-4 col-md-6">

            <div class="card-dashboard bg-green">

                <div class="card-overlay"></div>

                <div class="d-flex justify-content-between align-items-center">

                    <div>

                        <h5>Total Envois</h5>

                        <h2>${totalEnvois}</h2>

                        <div class="card-mini-text">
                            Transactions effectuées
                        </div>

                    </div>

                    <div class="card-icon">

                        <i class="bi bi-send-fill"></i>

                    </div>

                </div>

            </div>

        </div>

        <!-- RETRAITS -->
        <div class="col-lg-4 col-md-6">

            <div class="card-dashboard bg-red">

                <div class="card-overlay"></div>

                <div class="d-flex justify-content-between align-items-center">

                    <div>

                        <h5>Total Retraits</h5>

                        <h2>${totalRetraits}</h2>

                        <div class="card-mini-text">
                            Retraits réalisés
                        </div>

                    </div>

                    <div class="card-icon">

                        <i class="bi bi-bank"></i>

                    </div>

                </div>

            </div>

        </div>

        <!-- RECETTE -->
        <div class="col-lg-6 col-md-6">

            <div class="card-dashboard bg-orange">

                <div class="card-overlay"></div>

                <div class="d-flex justify-content-between align-items-center">

                    <div>

                        <h5>Recette Totale</h5>

                        <h2>${recette} Ar</h2>

                        <div class="card-mini-text">
                            Revenus générés
                        </div>

                    </div>

                    <div class="card-icon">

                        <i class="bi bi-wallet2"></i>

                    </div>

                </div>

            </div>

        </div>

        <!-- ARGENT TRANSFERE -->
        <div class="col-lg-6 col-md-6">

            <div class="card-dashboard bg-darkblue">

                <div class="card-overlay"></div>

                <div class="d-flex justify-content-between align-items-center">

                    <div>

                        <h5>Argent Transféré</h5>

                        <h2>${totalMontant} Ar</h2>

                        <div class="card-mini-text">
                            Montant global envoyé
                        </div>

                    </div>

                    <div class="card-icon">

                        <i class="bi bi-currency-exchange"></i>

                    </div>

                </div>

            </div>

        </div>

    </div>

    <!-- WELCOME SECTION -->
    <div class="welcome-section mt-4">

        <div class="row align-items-center">

            <!-- LEFT -->
            <div class="col-lg-8">

                <div class="welcome-badge">
                    MOBILE MONEY SYSTEM
                </div>

                <h2 class="welcome-title">
                    Bonjour Administrateur 👋
                </h2>

                <p class="welcome-text">

                    Bienvenue sur votre plateforme de gestion Mobile Money.
                    Gérez facilement les clients, transferts, retraits
                    et relevés PDF avec une interface moderne et sécurisée.

                </p>

                <div class="welcome-stats">

                    <div class="mini-stat">

                        <h3>${totalClients}</h3>

                        <p>Clients</p>

                    </div>

                    <div class="mini-stat">

                        <h3>${totalEnvois}</h3>

                        <p>Transactions</p>

                    </div>

                    <div class="mini-stat">

                        <h3>${recette} Ar</h3>

                        <p>Revenus</p>

                    </div>

                </div>

            </div>

            <!-- RIGHT -->
            <div class="col-lg-4 text-center">

                <div class="welcome-illustration">

                    <i class="bi bi-wallet2"></i>

                </div>

            </div>

        </div>

    </div>

</div>

<script src="assets/js/bootstrap.bundle.min.js"></script>

</body>
</html>