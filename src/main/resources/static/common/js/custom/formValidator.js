/**
 * Created by Tomas on 23-Sep-17.
 */

function icons(){
    let cross = document.getElementById('cross');
    let check = document.getElementById('check');
    cross.style.display='none';
    check.style.display='none';
}
function checkPass() {
    let cross = document.getElementById('cross');
    let check = document.getElementById('check');
    //Store the password field objects into variables ...
    let pass1 = document.getElementById('pass1');
    let pass2 = document.getElementById('pass2');
    //Store the Confimation Message Object ...
    //Set the colors we will be using ...
    let goodColor = "#66cc66";
    let badColor = "#ff6666";
    //Compare the values in the password field
    //and the confirmation field
    if(pass1.value === pass2.value){
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
}

 function isValidated(formName) {

    let message = "";
    let messageSpan = document.getElementById('messageSpan');
    let messageModal = $('#messageModal');


     if(formName==='register-form'){

         let nameInput = $('#nameInput').val();
         let lastNameInput = $('#lastNameInput').val();
         let emailInput =    $('#emailInput');
         let emailInputValue =    $('#emailInput').val();
         let usernameInput = $('#usernameInput').val();
         let newPassword = $('#pass1').val();
         let pass2 = $('#pass2');

         if (!checkPass()) {
             message = 'Las contraseñas deben coincidir!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(newPassword.length<7 || !/[0-9]/g.test(newPassword)){
             message = 'La contraseña debe ser mayor a 6 caracteres, poseer letras y números!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(nameInput.length===0){
             message = 'Debes escribir tu nombre!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(!/^[a-zA-Z\s]*$/g.test(nameInput)){
             message = 'El nombre debe contener solo letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(lastNameInput.length===0){
             message = 'Debes escribir tu apellido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
         } else if(!/^[a-zA-Z\s]*$/g.test(lastNameInput)){
             message = 'El apellido debe contener solo letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
         } else if(emailInputValue.length===0){
             message = 'Debes escribir tu email!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#emailInput").focus();
             return false;
         } else if(!emailInputValue.includes('@') || !emailInputValue.includes('.com')){
             message = 'Formato de email invalido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#emailInput").focus();
             return false;
         } else if(usernameInput.length===0){
             message = 'Debes escribir tu nombre de usuario!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
         } else if(!/^[a-zA-Z0-9]*$/g.test(usernameInput)){
             message = 'El nombre de usuario no puede contener carácteres!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
         }
         return true;
     }

     if(formName==='updateProfile-form'){

         let name = $("#name").val();
         let lastName = $("#lastName").val();
         let email = $("#email").val();
         let newPassword = $('#pass1');
         let pass2 = $('#pass2');

         if (name.length===0) {
             message = 'Debes escribir tu nombre!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#name").focus();
             return false;
         } else if (!/^[a-zA-Z\s]*$/g.test(name)){
             message = 'El nombre solo puede contener letras!';
             messageSpan.textContent  = message;
             messageModal.modal('show');
             $("#name").focus();
             return false;
         } else if (lastName.length===0){
             message = 'Debes escribir tu apellido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastName").focus();
             return false;
         } else if (!/^[a-zA-Z\s]*$/g.test(lastName)) {
             message = 'El apellido solo puede contener letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastName").focus();
             return false;
         } else if(newPassword.length===0 && pass2.length!==0){
             message = 'Debes ingresar la nueva contraseña!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(newPassword.length!==0 && pass2.length===0) {
             message = 'Debes repetir la contraseña!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass2").focus();
             return false;
         } else if(!checkPass()){
             message = 'Las contraseñas deben coincidir!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             return false;
         }
     return true;
     }

    if(formName==='newItemForm'){

        let selectValue = document.getElementById('selectCat').value;
        let itemName = $("#itemName").val();
        let itemDes = $("#descTextArea").val();

        if (selectValue === '-1') {
            message = 'Debe seleccionar una categoría para crear el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            document.getElementById('selectCat').focus();
            return false;

        } else if (itemName.length===0) {
            message = 'Debe asignar un nombre el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#itemName").focus();
            return false;

        } else if (!/^[a-zA-Z\s]*$/g.test(itemName)){
            message = 'El nombre solo puede contener letras!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#itemName").focus();
            return false;

        } else if (itemDes.length===0){
            message = 'Debes describir el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#descTextArea").focus();
            return false;

        } else return true;

    }



}