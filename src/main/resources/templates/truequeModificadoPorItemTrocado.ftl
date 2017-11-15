<html>

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
                <table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                    <tbody>
                    <tr>
                        <td>
                            <p>${userTargetName}, recientemente ${userOriginName} ha confirmado un trueque con los siguientes items:</p>
                            <#list items>
                                <#items as item>
                                    <p> - ${item.name}</p>
                                </#items>
                            </#list>
                            <p>Existe un trueque activo entre ustedes dos por lo que te sugerimos que te comuniques con él a través del chat para modificar los items y coordinen su trueque</p>
                            <p>Esperamos que tengas un excelente día.</p>
                        </td>
                    </tr>
                </table>
                <br/>
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