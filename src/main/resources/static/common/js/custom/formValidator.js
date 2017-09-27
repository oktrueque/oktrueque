/**
 * Created by Tomas on 23-Sep-17.
 */

function icons(){
    let cross = document.getElementById('cross');
    let check = document.getElementById('check');
    cross.style.display='none';
    check.style.display='none';
    check.style.color = "#ffffff";
    cross.style.color = "#ffffff";
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

function isEmpty(val) {

    // test results
    //---------------
    // []        true, empty array
    // {}        true, empty object
    // null      true
    // undefined true
    // ""        true, empty string
    // ''        true, empty string
    // 0         false, number
    // true      false, boolean
    // false     false, boolean
    // Date      false
    // function  false

    if (val === undefined)
        return true;

    if (typeof (val) == 'function' || typeof (val) == 'number' || typeof (val) == 'boolean' || Object.prototype.toString.call(val) === '[object Date]')
        return false;

    if (val == null || val.length === 0)        // null or 0 length array
        return true;

    if (typeof (val) == "object") {
        // empty object

        var r = true;

        for (var f in val)
            r = false;

        return r;
    }

    if((!val || /^\s*$/.test(val))){return true;}

    return false;
}

function hasOnlyLetterAndSpaces(val) {

    if(!/^[a-zA-Z\s]*$/g.test(val)) {return true;}

    return false;
}

function hasCharacters(val) {

    if(!/^[a-zA-Z0-9]*$/g.test(val)) {return true;}

    return false;
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

          if(isEmpty(nameInput)){
             message = 'Debes escribir tu nombre!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(hasOnlyLetterAndSpaces(nameInput)){
             message = 'El nombre debe contener solo letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(isEmpty(lastNameInput)){
             message = 'Debes escribir tu apellido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
         } else if(hasOnlyLetterAndSpaces(lastNameInput)){
             message = 'El apellido debe contener solo letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
         } else if(isEmpty(emailInputValue)){
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
         } else if(isEmpty(usernameInput)){
             message = 'Debes escribir tu nombre de usuario!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
         } else if(hasCharacters(usernameInput)){
             message = 'El nombre de usuario no puede contener carácteres!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
         } else if (!checkPass()) {
             message = 'Las contraseñas deben coincidir!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(newPassword.length<7 || !/[0-9]/g.test(newPassword)){
             message = 'La contraseña debe ser mayor a 6 caracteres, poseer letras y números!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         }
         return true;
     }

     if(formName==='updateProfile-form'){

         let name = $("#name").val();
         let lastName = $("#lastName").val();
         let email = $("#email").val();
         let newPassword = $('#pass1');
         let newPasswordVal = $('#pass1').val();
         let pass2 = $('#pass2');
         let pass2Val = $('#pass2').val();

         if (isEmpty(name)) {
             message = 'Debes escribir tu nombre!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#name").focus();
             return false;
         } else if (hasOnlyLetterAndSpaces(name)){
             message = 'El nombre solo puede contener letras!';
             messageSpan.textContent  = message;
             messageModal.modal('show');
             $("#name").focus();
             return false;
         } else if (isEmpty(lastName)){
             message = 'Debes escribir tu apellido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastName").focus();
             return false;
         } else if (hasOnlyLetterAndSpaces(lastName)) {
             message = 'El apellido solo puede contener letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastName").focus();
             return false;
         } else if(isEmpty(newPassword) && !isEmpty(pass2)){
             message = 'Debes ingresar la nueva contraseña!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(!isEmpty(newPassword) && isEmpty(pass2)) {
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
            message = 'Debes seleccionar una categoría para crear el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            document.getElementById('selectCat').focus();
            return false;

        } else if (isEmpty(itemName)) {
            message = 'Debes asignar un nombre el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#itemName").focus();
            return false;

        }  else if (isEmpty(itemDes)){
            message = 'Debes describir el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#descTextArea").focus();
            return false;

        } else return true;

    }

    if(formName==='form-set-images'){

        let photo1 = $('#photo1').val();
        let photo2 = $('#photo2').val();
        let photo3 = $('#photo3').val();

        if(photo1!==''||photo2!==''||photo3!==''){
            return true;
        } else if(photo1===''&&photo2===''&&photo3===''){
            message = 'Debes cargar al menos una imagen!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#photo1").focus();
            return false;
        }

    }



}