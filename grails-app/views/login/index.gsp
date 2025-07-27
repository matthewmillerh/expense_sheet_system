<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Login - Expense Tracker</title>
</head>
<body>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-12">
            <div class="card shadow-sm mx-auto" style="max-width: 500px;">
                <div class="card-body p-4">
                    <h2 class="card-title text-center mb-4"><strong>Welcome to the Expense Tracker</strong></h2>
                    <g:form controller="login" action="login">
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <g:textField name="name" class="form-control" required="true" placeholder="Enter your name"/>
                        </div>
                        <div class="d-grid">
                            <g:submitButton name="login" value="Continue" class="btn btn-secondary"/>
                        </div>
                    </g:form>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>