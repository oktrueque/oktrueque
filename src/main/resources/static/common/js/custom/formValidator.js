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

function hasCorrectLength(limite,lentgh){
    return limite >= lentgh;
}

function isEmpty(val) {
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
    if(!/^[ A-Za-zäÄëËïÏöÖüÜáéíóúáéíóúÁÉÍÓÚÂÊÎÔÛâêîôûàèìòùÀÈÌÒÙñ ,.'-]+$/g.test(val)) {return true;}
    return false;
}

function isAnEmail(val) {
    if(!/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/g.test(val)) {return false;}
    return true;
}

function isAnUsername(val) {
    if(!/^[a-zñ](([\._\-][a-zñ0-9])|[a-zñ0-9])*[a-zñ0-9]$/g.test(val)) {return false;}
    return true;
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

         let nameInput= $('#nameInput');
         let nameInputVal = $('#nameInput').val();
         let nameInputLength = $('#nameInput').val().length;

         let lastNameInput = $('#lastNameInput');
         let lastNameInputVal = $('#lastNameInput').val();
         let lastNameInputLength = $('#lastNameInput').val().length;

         let emailInput =    $('#emailInput');
         let emailInputVal =    $('#emailInput').val();
         let emailInputLength = $('#emailInput').val().length;

         let usernameInput = $('#usernameInput');
         let usernameInputVal = $('#usernameInput').val();
         let userNameInputLength = $('#usernameInput').val().length;

         let firstPassInput = $('#pass1');
         let firstPassVal = $('#pass1').val();
         let firstPassLength = $('#pass1').val().length;

         let pass2 = $('#pass2');

          if(isEmpty(nameInputVal)){
             message = 'Debes escribir tu nombre!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#nameInput").focus();
             return false;
         } else if(hasOnlyLetterAndSpaces(nameInputVal)) {
              message = 'El nombre debe contener solo letras!';
              messageSpan.textContent = message;
              messageModal.modal('show');
              $("#nameInput").focus();
              return false;
         } else if(!hasCorrectLength(100, nameInputLength)){
              message = 'El nombre es demasiado largo';
              messageSpan.textContent = message;
              messageModal.modal('show');
              $("#nameInput").focus();
              return false;
         } else if(isEmpty(lastNameInputVal)){
             message = 'Debes escribir tu apellido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
         } else if(hasOnlyLetterAndSpaces(lastNameInputVal)){
             message = 'El apellido debe contener solo letras!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#lastNameInput").focus();
             return false;
          } else if(!hasCorrectLength(100, lastNameInputLength)){
              message = 'El apellido es demasiado largo';
              messageSpan.textContent = message;
              messageModal.modal('show');
              $("#lastNameInput").focus();
              return false;
         } else if(isEmpty(emailInputVal)){
             message = 'Debes escribir tu email!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#emailInput").focus();
             return false;
         } else if(!isAnEmail(emailInputVal)){
             message = 'Formato de email invalido!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#emailInput").focus();
             return false;
          } else if(!hasCorrectLength(100, emailInputLength)){
              message = 'El email es demasiado largo';
              messageSpan.textContent = message;
              messageModal.modal('show');
              $("#emailInput").focus();
              return false;
         } else if(isEmpty(usernameInputVal)){
             message = 'Debes escribir tu nombre de usuario!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
         } else if(!isAnUsername(usernameInputVal)){
             message = 'El nombre de usuario no puede contener carácteres especiales!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#usernameInput").focus();
             return false;
          } else if(!hasCorrectLength(100, userNameInputLength)){
              message = 'El nombre de usuario es demasiado largo!';
              messageSpan.textContent = message;
              messageModal.modal('show');
              $("#usernameInput").focus();
              return false;
         } else if (!checkPass()) {
             message = 'Las contraseñas ingresadas no coinciden!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(firstPassVal.length<6 || !/[0-9]/g.test(firstPassVal)){
             message = 'La contraseña debe ser mayor a 6 caracteres, poseer letras y números!';
             messageSpan.textContent = message;
             messageModal.modal('show');
             $("#pass1").focus();
             return false;
         } else if(!document.getElementById('terminosCondicionesInput').checked) {
              message = 'Debes aceptar los terminos y condiciones para registrarte';
              messageSpan.textContent = message;
              messageModal.modal('show');
              return false;
          } else return true;
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
         } else return true;
     }

    if(formName==='newItemForm'){

        let selectValue = document.getElementById('selectCat').value;
        let itemNameInputVal = $("#itemName").val();
        let itemDesInputVal = $("#descTextArea").val();
        let itemNameInputLength = $("#itemName").val().length;
        let itemDesInputLength = $("#descTextArea").val().length;

        if (selectValue === '-1') {
            message = 'Debes seleccionar una categoría para crear el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            document.getElementById('selectCat').focus();
            return false;
        } else if (isEmpty(itemNameInputVal)) {
            message = 'Debes asignar un nombre el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#itemName").focus();
            return false;
        }  else if (isEmpty(itemDesInputVal)){
            message = 'Debes describir el item!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#descTextArea").focus();
            return false;
        } else if(!hasCorrectLength(30,itemNameInputLength)){
            message = 'El nombre del item es demasiado largo!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#itemName").focus();
            return false;
        } else if(!hasCorrectLength(200,itemDesInputLength)){
            message = 'La descripcion es demasiado larga!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#descTextArea").focus();
            return false;
        } else  return true;
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

    if(formName==='frm-complaint'){

        let selectValue = document.getElementById('complaint-type').value;
        let descriptionInputVal = $('#description').val();
        let descriptionInputLength = $('#description').val().length;

        if (selectValue === '-1') {
            message = 'Debes seleccionar un tipo de denuncia para realizarla!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            document.getElementById('complaint-type').focus();
            return false;
        } else if (isEmpty(descriptionInputVal)) {
            message = 'Debes describir la denuncia!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#description").focus();
            return false;
        } else if(!hasCorrectLength(200,descriptionInputLength)){
            message = 'La descripcion es demasiado larga!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#description").focus();
            return false;

        } else return true;

    }

    if(formName==='contactUsForm'){

        let nameAndLastnameInputVal = $("#nameAndLastNameInput").val();
        let nameAndLastnameInputLength = $("#nameAndLastNameInput").val().length;
        let emailInputVal = $("#emailInput").val();
        let emailInputLength = $("#emailInput").val().length;
        let messageInputVal = $("#messageInput").val();
        let messageInputLength = $("#messageInput").val().length;

        if (isEmpty(nameAndLastnameInputVal)) {
            message = 'Debes escribir tu nombre!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#nameAndLastNameInput").focus();
            return false;
        } else if(isEmpty(emailInputVal) || !emailInputVal.includes('@') || !emailInputVal.includes('.com')){
            message = 'Debes escribir tu mail o el formato de tu mail es incorrecto!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#emailInput").focus();
            return false;
        } else if(isEmpty(messageInputVal)){
            message = 'Debes escribir tu mensaje!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#messageInput").focus();
            return false;
        } else if(!hasCorrectLength(40,nameAndLastnameInputLength)){
            message = 'El nombre es demasiado largo!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#nameAndLastNameInput").focus();
            return false;
        } else if(!hasCorrectLength(40,emailInputLength)){
            message = 'El mail es demasiado largo!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#emailInput").focus();
            return false;
        } else if(!hasCorrectLength(250,messageInputLength)){
            message = 'El mensaje es demasiado largo!';
            messageSpan.textContent  = message;
            messageModal.modal('show');
            $("#messageInput").focus();
            return false;
        } else  return true;
    }

    if(formName==='commentModal'){

      let scoreComboBox = document.getElementById('scoreComboBox').value;
      let commentDescripcionVal = $('#commentDescription').val();
      let commentDescripcionLength = $('#commentDescription').val().length;

      if(isEmpty(commentDescripcionVal)){
          message = 'Debes describir el comentario!';
          messageSpan.textContent  = message;
          messageModal.modal('show');
          $("#commentDescription").focus();
          return false;
      } else if(scoreComboBox==='-1'){
          message = 'Debes seleccionar una calificacion!';
          messageSpan.textContent  = message;
          messageModal.modal('show');
          $("#scoreComboBox").focus();
          return false;
      } else if(!hasCorrectLength(150,commentDescripcionLength)){
          message = 'El comentario es demasiado largo!';
          messageSpan.textContent  = message;
          messageModal.modal('show');
          $("#commentDescription").focus();
          return false;
      } else return true;
    }



}