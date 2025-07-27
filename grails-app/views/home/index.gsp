<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
</head>
<body>
<content tag="nav">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Application Status <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li class="dropdown-item"><a href="#">Environment: ${grails.util.Environment.current.name}</a></li>
            <li class="dropdown-item"><a href="#">App version:
                <g:meta name="info.app.version"/></a>
            </li>
            <li role="separator" class="dropdown-divider"></li>
            <li class="dropdown-item"><a href="#">Grails version:
                <g:meta name="info.app.grailsVersion"/></a>
            </li>
            <li class="dropdown-item"><a href="#">Groovy version: ${GroovySystem.getVersion()}</a></li>
            <li class="dropdown-item"><a href="#">JVM version: ${System.getProperty('java.version')}</a></li>
            <li role="separator" class="dropdown-divider"></li>
            <li class="dropdown-item"><a href="#">Reloading active: ${grails.util.Environment.reloadingAgentEnabled}</a></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Artefacts <span class="caret"></span></a>
        <ul class="dropdown-menu">
            <li class="dropdown-item"><a href="#">Controllers: ${grailsApplication.controllerClasses.size()}</a></li>
            <li class="dropdown-item"><a href="#">Domains: ${grailsApplication.domainClasses.size()}</a></li>
            <li class="dropdown-item"><a href="#">Services: ${grailsApplication.serviceClasses.size()}</a></li>
            <li class="dropdown-item"><a href="#">Tag Libraries: ${grailsApplication.tagLibClasses.size()}</a></li>
        </ul>
    </li>
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Installed Plugins <span class="caret"></span></a>
        <ul class="dropdown-menu dropdown-menu-right">
            <g:each var="plugin" in="${applicationContext.getBean('pluginManager').allPlugins}">
                <li class="dropdown-item"><a href="#">${plugin.name} - ${plugin.version}</a></li>
            </g:each>
        </ul>
    </li>
</content>

<div class="svg" role="presentation">
    <div class="grails-logo-container">
        <asset:image src="grails-cupsonly-logo-white.svg" class="grails-logo"/>
    </div>
</div>

<div id="content" role="main">
    <div class="container">
        <section class="row colset-2-its">
            <h1>Expense Sheet System Home</h1>

            <p class="text-center mx-auto">
                Welcome to the Expense Sheet System! Use this application to track your expenses, view your expense history, and export your data to CSV. 
            </p>

            <%-- Show user balances --%>
            <div class="mx-auto d-block w-100">
                <h2 class="text-center"><strong>Your Balances</strong></h2>
                <div class="text-center">
                    <p>Starting Balance: <strong>R<g:formatNumber number="${startingBalance ?: 0}" type="number" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
                    <p>Current Balance: <strong>R<g:formatNumber number="${currentBalance ?: 0}" type="number" minFractionDigits="2" maxFractionDigits="2"/></strong></p>
                </div>
            </div>

            <%-- Quick actions for authenticated users --%>
            <div id="controllers" role="navigation" class="mx-auto">
                <h2 class="text-center">Quick Actions:</h2>
                <ul class="list-unstyled d-flex flex-wrap justify-content-center">
                    <li class="controller list-inline-item">
                        <g:link controller="expense" action="create" class="text-white"><button class="btn btn-secondary text-white">Add Expense</button></g:link>
                    </li>
                    <li class="controller list-inline-item">
                        <g:link controller="expense" action="index" class="text-white"><button class="btn btn-secondary text-white">View Expenses</button></g:link>
                    </li>
                    <li class="controller list-inline-item">
                        <g:link controller="user" action="index" class="text-white"><button class="btn btn-secondary text-white">View Account</button></g:link>
                    </li>
                </ul>
            </div>
        </section>
    </div>
</div>

</body>
</html>
