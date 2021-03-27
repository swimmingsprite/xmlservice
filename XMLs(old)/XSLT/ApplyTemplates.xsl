<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <b>Order Id: <xsl:value-of select="Order/@id"/></b>
                <xsl:apply-templates />
            </body>
        </html>
    </xsl:template>

    <xsl:template match="CustomerName">
        <br/>Customer name: <xsl:value-of select="."/>
    </xsl:template>

    <xsl:template match="Item">
        <br/> Item name: <xsl:value-of select="ItemName"/>
        <br/>Item price: <xsl:value-of select="Price"/>
    </xsl:template>
</xsl:stylesheet>