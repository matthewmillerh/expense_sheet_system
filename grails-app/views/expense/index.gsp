<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'expense.label', default: 'Expense')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    <div id="content" role="main">
        <div class="container">
            <section class="row">
                <a href="#list-expense" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
                <div class="nav" role="navigation">
                    <ul>
                        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
                        <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
                    </ul>
                </div>
            </section>
            <section class="row">
                <div id="list-expense" class="col-12 content scaffold-list" role="main">
                    <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                    <g:if test="${flash.message}">
                        <div class="message" role="status">${flash.message}</div>
                    </g:if>

                    <g:if test="${expenseList.empty}">
                        <div class="message" role="status">
                            <g:message code="default.no.expenses.message" default="No expenses recorded yet." />
                        </div>
                    </g:if>
                    <g:else>
                        <f:table 
                            collection="${expenseList}" 
                            properties="['description', 'amount', 'date', 'runningBalance', 'usdAmount']" 
                            labels="['Description', 'Amount (ZAR)', 'Date', 'Running Balance (ZAR)', 'Amount (USD)']"
                        />
                    </g:else>

                    <g:if test="${expenseCount > params.int('max')}">
                    <div class="pagination">
                        <g:paginate total="${expenseCount ?: 0}" />
                    </div>
                    </g:if>
                </div>
            </section>
        </div>
    </div>
    </body>
</html>