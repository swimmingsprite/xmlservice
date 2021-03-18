<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <html>
            <body>
                <h1>Order information:
                    <xsl:apply-templates select="Order/CustomerName"/>
                    <xsl:apply-templates select="Order/Item">
                        <xsl:sort select="Price" order="descending" />
                    </xsl:apply-templates>
                    <h5>
                        Total number of items:
                        <xsl:value-of select="count(Order/Item)"/>
                    </h5>
                    <h5>
                        Total price:
                        <xsl:value-of select="sum(Order/Item/Price)"/>
                    </h5>
                </h1>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="CustomerName">
        <h3>
            Customer name:
            <xsl:value-of select="."/>
        </h3>
    </xsl:template>


    <xsl:template match="Item">
        <p>
            <b>Order Id:
                <xsl:value-of select="../@id"/>
            </b>
            <br/>
            <b>Price:
                <xsl:value-of select="round(Price)"/>
            </b>
            <br/>
            <b>Item name:
                <xsl:value-of select="translate(Item/ItemName, 'abcdefghijklmnopqrstuvxyz', 'ABCDEFGHIJKLMNOPQRSTUVXYZ' )"/>

            </b>
            <br/>
            <xsl:if test="Price>100">
                <b>Free shipping</b>
                <br/>
            </xsl:if>
        </p>
    </xsl:template>


</xsl:stylesheet>