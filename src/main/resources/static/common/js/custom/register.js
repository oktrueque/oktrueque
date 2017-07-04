

function checkPass()
{
    //Store the password field objects into variables ...
    var pass1 = document.getElementById('validation[password]');
    var pass2 = document.getElementById('pass2');
    //Store the Confimation Message Object ...
    var message = document.getElementById('confirmMessage');
    //Set the colors we will be using ...
    var goodColor = "#66cc66";
    var badColor = "#ff6666";
    //Compare the values in the password field 
    //and the confirmation field
    if(pass1.value == pass2.value){
        //The passwords match. 
        //Set the color to the good color and inform
        //the user that they have entered the correct password
        cross.style.display='none';
        check.style.display='inline';
        check.style.color = goodColor;
        return true;
    }else{
        //The passwords do not match.
        //Set the color to the bad color and
        //notify the user.
        check.style.display='none';
        cross.style.display='inline';
        cross.style.color = badColor;
        return false;
    }
};

function icons(){
    cross.style.display='none';
    check.style.display='none';
};

$('#btn-register').click(function(){
    if(checkPass()){
        if(document.getElementById('form-checkbox').checked){
            $('#form-register').submit();
        }
    }else{
        console.log("Algun campo del form tiene problemas")
    }
});