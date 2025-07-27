<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Expense Sheet System - Setup Balance</title>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="card shadow-sm mx-auto" style="max-width: 500px;">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4"><strong>Set Your Starting Balance</strong></h2>
                    <p class="text-center mb-4">Welcome, <strong>${user.name}!</strong> Please enter your starting balance to continue.</p>

                    <g:if test="${flash.message}">
                        <div class="alert alert-danger" role="alert">
                            ${flash.message}
                        </div>
                    </g:if>

                    <g:form controller="login" action="saveBalance">
                        <div class="mb-3">
                            <label for="startingBalance" class="form-label">Starting Balance (ZAR):</label>
                            <g:field type="number" name="startingBalance" class="form-control" step="0.01" required="true" placeholder="Enter your starting balance"/>
                        </div>
                        <div class="d-grid">
                            <g:submitButton name="save" value="Save and Continue" class="btn btn-secondary"/>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
// Force a reload of the page if it was loaded from the cache
window.addEventListener('pageshow', function(event) {
    if (event.persisted) {
        window.location.reload();
    }
});
</script>

</body>
</html>