
<head></head>

<body>
<div class="example-emails margin-bottom-50">
    <div width="100%" style="background: #eceff4; padding: 50px 20px; color: #514d6a;">
        <div style="max-width: 700px; margin: 0px auto; font-size: 14px">
            <table border="0" cellpadding="0" cellspacing="0" style="width: 100%; margin-bottom: 20px">
                <tr>
                    <td style="vertical-align: top;">
                        <img src="https://image.ibb.co/fvqFwk/logo_landing.png" style="height: 40px"/>
                    </td>
                </tr>
            </table>
            <div style="padding: 40px 40px 20px 40px; background: #fff;">
                <p>Hola ${user.getName()}!</p>
                <p>Tu password se ha restablecido.</p>
                <p>Nuevo password: ${rawPassword}</p>

                <p>Ingresa a tu perfil y restablece una nueva contraseña por tu seguridad.</p>

                <a href="${uri_editar_perfil}"
                   style="display: inline-block; padding: 11px 30px; margin: 20px 0px 30px; font-size: 15px; color: #fff; background: #01a8fe; border-radius: 5px">
                    Editar perfil
                </a>
            </div>
            <div style="text-align: center; font-size: 12px; color: #a09bb9; margin-top: 20px">
                <p>
                    OkTrueque Software Inc., Independencia 864, Cordoba, Argentina.
                    <br/>
                    www.oktrueque.com
                </p>
            </div>
        </div>
    </div>
</div>
</body>

</html>