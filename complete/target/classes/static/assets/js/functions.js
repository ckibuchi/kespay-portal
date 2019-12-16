function openModal() {
    document.getElementById('modal').style.display = 'block';
    document.getElementById('fade').style.display = 'block';
}
function closeModal() {
    document.getElementById('modal').style.display = 'none';
    document.getElementById('fade').style.display = 'none';
}
/*<![CDATA[*/
function submitEmployee(){

    $(".loading").show();
    var dob = Date.parse($("#doB").val());
    var joining = Date.parse($("#joiningDate").val());
    var formData={
        phone: $("#phone").val(),
        user : {
            username : $("#username").val(),
            email : $("#email").val(),
            password:$("#password").val(),
            firstName:$("#firstName").val(),
            lastName:$("#lastName").val(),
            surName:$("#surName").val(),
            doB:dob.toString('yyyy-MM-dd'),
            gender:$("#gender").val(),
            joiningDate:joining.toString('yyyy-MM-dd')
        }
    }

    $.ajax({
        url : "/newemployee",
        type : 'POST',
        //data :JSON.stringify(user),
        data :JSON.stringify(formData),//JSON.stringify($("#add_employee_form").serializeObject()),
        contentType : "application/json",
        success : function(data, textStatus, jqXHR) {

            $('#modal-body').replaceWith(data);
            $(".loading").hide();
        },
        error: function(jqXHR, textStatus, errorThrown){
            $(".loading").hide();
            console.log(jqXHR.responseJSON.error);
            alert(jqXHR.responseJSON.status+' :' +jqXHR.responseJSON.error);
        }
    });
}
/*]]>*/

/*<![CDATA[*/
function searchEmployees(){

    var formData={
        name: $("#name").val(),
        empNo: $("#empNo").val(),
    }

    $.ajax({
        url : "/searchemps",
        type : 'POST',
        //data :JSON.stringify(user),
        data :JSON.stringify(formData),//JSON.stringify($("#add_employee_form").serializeObject()),
        contentType : "application/json",
        success : function(data, textStatus, jqXHR) {

            $('#staff-grid-row').replaceWith(data);

        },
        error: function(jqXHR, textStatus, errorThrown){

            console.log(jqXHR.responseJSON.error);
            alert(jqXHR.responseJSON.status+' :' +jqXHR.responseJSON.error);
        }
    });
}
/*]]>*/



$(document).ready(function(){
    $(".loading").hide();

    var empno = document.getElementById("empNo");
    var name = document.getElementById("name");

// Execute a function when the user releases a key on the keyboard
    empno.addEventListener("keyup", function(event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Trigger the button element with a click
            searchEmployees();
        }
    });g

    name.addEventListener("keyup", function(event) {
        // Cancel the default action, if needed
        event.preventDefault();
        // Number 13 is the "Enter" key on the keyboard
        if (event.keyCode === 13) {
            // Trigger the button element with a click
            searchEmployees();
        }
    });

});