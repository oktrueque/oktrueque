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
                            <p>Buenas ${nombreDestino} ${apellidoDestino}</p>
                            <p>${nombreOrigen} ${apellidoOrigen} quiere realizar un trueque contigo.</p>
                            </table>
                            <br/>
                            <h5 style="margin-bottom: 20px; color: #24222f; font-weight: 600">Items Propuestos</h5>
                            <table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                            <#list itemsPropuestos>
                                <tr>
                                    <#items as itemPropuesto>
                                        <td style="text-align: left; padding: 10px 10px 10px 0px; border-top: 1px solid #d9d7e0; white-space: nowrap; vertical-align: top">
                                            ${itemPropuesto.name}
                                        </td>
                                        <td style="width: 50%;padding: 10px 0px 10px 10px; border-top: 1px solid #d9d7e0;">
                                            ${itemPropuesto.description}
                                            <br/>
                                            ${itemPropuesto.category.name}
                                        </td>
                                    </#items>
                                </tr>
                            </#list>
                            </table>
                            <h5 style="margin-bottom: 20px; color: #24222f; font-weight: 600">Tus Items</h5>
                            <table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
                            <#list itemsDemandados>
                                <tr>
                                    <#items as itemDemandado>
                                        <td style="text-align: left; padding: 10px 10px 10px 0px; border-top: 1px solid #d9d7e0; white-space: nowrap; vertical-align: top">
                                        ${itemDemandado.name}
                                        </td>
                                        <td style="width: 50%;padding: 10px 0px 10px 10px; border-top: 1px solid #d9d7e0;">
                                        ${itemDemandado.description}
                                            <br/>
                                        ${itemDemandado.category.name}
                                        </td>
                                    </#items>
                                </tr>
                            </#list>
                            </table>
                <a href="${uri_confirm}"
                   style="display: inline-block; padding: 11px 30px; margin: 20px 0px 30px; font-size: 15px; color: #fff; background: #01a8fe; border-radius: 5px">
                    Aceptar Trueque
                </a>
                </td>
                            </tr>
                    </tbody>
                </table>
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