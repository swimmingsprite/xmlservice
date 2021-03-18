<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <div class="root">
                    <div class="upper-panel">
                        <div class="left upanel-child bottom-border">
                            <div class="bottom-border">
                                <xsl:if test="/Document/GeneralType = 'Tax document'">
                                    <p><b>Daňový doklad</b></p>
                                </xsl:if>
                                <xsl:if test="/Document/Type = 'Invoice'">
                                    <h3 class="low-margin">Faktúra č. <xsl:value-of select="/Document/Number"/></h3>
                                </xsl:if>
                            </div>
                            <div class="dates">
                                <p>
                                    <span>Dátum dodania služby:</span>
                                    <span class="date-span">21.10.2020</span>
                                </p>
                                <p>
                                    <span>Dátum vystavenia:</span>
                                    <span class="date-span">21.10.2020</span>
                                </p>
                                <p>
                                    <span>Dátum splatnosti:</span>
                                    <span class="date-span">21.10.2020</span>
                                </p>
                                <br/>
                            </div>
                            <div>
                                <p>Forma úhrady: Bankový prevod</p>
                                <p>KS: 308</p>
                                <p>VS: 12345 /č.fakt</p>
                                <br/>
                            </div>
                            <p>
                                <b>Banka: Tatra Banka</b>
                            </p>
                            <p>
                                <b>Číslo účtu: SK45 1100 0000 0026 1234 5678</b>
                            </p>
                        </div>


                        <div class="right upanel-child">
                            <div class="name">
                                <b>Dodávateľ:</b>
                            </div>
                            <div class="bottom-border">
                                <p class="sup-address">
                                    Firma s.r.o.
                                    <br/>
                                    Uličná 18/7654
                                    <br/>
                                    083 12 Bratislava
                                </p>
                                <br/>
                                <p>
                                    <b>IČ DPH: SK2045123456</b>
                                </p>
                                <p>
                                    <b>DIČ: 2045123456</b>
                                </p>
                            </div>
                            <div class="name">
                                <b>Odberateľ:</b>
                            </div>
                            <div class="bottom-border">
                                <br/>
                                <p>
                                    <b>Odberateľ s.r.o.</b>
                                </p>
                                <p>Nejaká ulica 12</p>
                                <p>
                                    <b>965 04 Trenčín</b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>IČO : 98725845 IČ DPH :SK 2098725845</b>
                                </p>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>DIČ : 2098725845</b>
                                </p>
                            </div>
                        </div>
                    </div>

                    <div class="middle-panel">
                        <p>Fakturujem Vám za služby v mesiaci Apríl 2020 podľa príloh
                            <br/>
                            <br/>
                            <span>
                                <b>čiastku 4825,85 EUR</b>
                            </span>
                        </p>
                    </div>

                    <div class="bottom_panel">
                        <div class="bottom-left">
                            <div class="tax-payer bottom-border">
                                <p>Platca DPH</p>
                            </div>
                            <div class="bottom-border">
                                <p>Faktúru vystavil: Zamestnanec</p>
                            </div>
                            <div class="signature">
                                <p>Pečiatka a podpis:</p>
                            </div>


                        </div>


                        <div class="bottom-right">
                            <div class="bottom-border">
                                <p>
                                    <b>Základ bez dph:
                                        <span class="date-span">4000 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>DPH 20%:
                                        <span class="date-span">825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Cena vrátane DPH:
                                        <span class="date-span">4825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                            </div>
                            <div class="bottom-border">
                                <p>
                                    <b>Čiastka k úhrade:
                                        <span class="date-span">4825,85 EUR</span>
                                    </b>
                                </p>
                                <br/>
                                <br/>
                            </div>
                        </div>
                    </div>

                </div>


            </body>
        </html>

    </xsl:template>
</xsl:stylesheet>