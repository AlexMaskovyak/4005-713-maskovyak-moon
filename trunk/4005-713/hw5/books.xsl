<xsl:stylesheet version='1.1'
    xmlns:xsl='http://www.w3.org/1999/XSL/Transform'>
  <xsl:output method='html' encoding='utf-8'/>

  <xsl:template match="/">
    <html>
      <xsl:apply-templates/>
    </html>
  </xsl:template>

  <xsl:template match="head">
    <xsl:copy>
      <xsl:apply-templates select="title"/>
      <script src="hw5.js"/>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="title">
    <xsl:copy>
      <xsl:apply-templates />
    </xsl:copy>
  </xsl:template>

  <xsl:template match="body">
    <xsl:copy>
      <table border="1">      
	<tr>
	  <th>Books in my office</th>
	  <th>Author</th>
	  <th>Publisher</th>
	  <th>ISBN</th>
	  <th>Year</th>
	</tr>
	<xsl:apply-templates select="outline/outline"/>
      </table>
    </xsl:copy>
  </xsl:template>

  <xsl:template match="outline/outline">
    <tr>
      <td><xsl:apply-templates select="@title"/></td>
      <td><xsl:apply-templates select="@author"/></td>
      <td><xsl:apply-templates select="@publisher"/></td>
      <td><xsl:apply-templates select="@isbn"/></td>
      <td><xsl:apply-templates select="@year"/></td>
    </tr>
  </xsl:template>
  
</xsl:stylesheet>
