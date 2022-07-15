<?xml version="1.0" encoding="UTF-8"?>
<!--
  ~ Copyright (c) 2006, Bjorn LLC. All Rights Reserved.
  -->
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xCal="http://ietf.org/rfc/rfc2245.txt">
	<xsl:strip-space elements="*"/>
	<xsl:preserve-space elements="text"/>
	<xsl:output method="text" media-type="text/calendar" encoding="utf-8"/>
	<xsl:template match="/">
		<xsl:apply-templates select="iCalendar"/>
	</xsl:template>
	<xsl:template match="iCalendar">
		<xsl:apply-templates select="vcalendar"/>
	</xsl:template>
	<xsl:template match="vcalendar">
		<xsl:text>BEGIN:VCALENDAR
</xsl:text>
		<xsl:for-each select="vevent">
			<xsl:text>BEGIN:VEVENT
</xsl:text>
			<xsl:for-each select="valarm">
				<xsl:text>BEGIN:VALARM
</xsl:text>
				<xsl:apply-templates select="*"/>
				<xsl:text>END:VALARM
</xsl:text>
			</xsl:for-each>
			<xsl:apply-templates select="*"/>
			<xsl:text>END:VEVENT
</xsl:text>
		</xsl:for-each>
		<xsl:for-each select="vtodo">
			<xsl:text>BEGIN:VTODO
</xsl:text>
			<xsl:for-each select="valarm">
				<xsl:text>BEGIN:VALARM
</xsl:text>
				<xsl:apply-templates select="*"/>
				<xsl:text>END:VALARM
</xsl:text>
			</xsl:for-each>
			<xsl:apply-templates select="*"/>
			<xsl:text>END:VTODO
</xsl:text>
		</xsl:for-each>
		<xsl:for-each select="vcard">
			<xsl:text>BEGIN:VCARD
</xsl:text>
			<xsl:apply-templates select="*"/>
			<xsl:text>END:VCARD
</xsl:text>
		</xsl:for-each>
		<xsl:for-each select="vjournal">
			<xsl:text>BEGIN:VJOURNAL
</xsl:text>
			<xsl:for-each select="valarm">
				<xsl:text>BEGIN:VALARM
</xsl:text>
				<xsl:apply-templates select="*"/>
				<xsl:text>END:VALARM
</xsl:text>
			</xsl:for-each>
			<xsl:apply-templates select="*"/>
			<xsl:text>END:VJOURNAL
</xsl:text>
		</xsl:for-each>
		<xsl:for-each select="vtimezone">
			<xsl:text>BEGIN:VTIMEZONE
</xsl:text>
			<xsl:for-each select="standard">
				<xsl:text>BEGIN:STANDARD
</xsl:text>
				<xsl:apply-templates select="*"/>
				<xsl:text>END:STANDARD
</xsl:text>
			</xsl:for-each>
			<xsl:for-each select="daylight">
				<xsl:text>BEGIN:DAYLIGHT
</xsl:text>
				<xsl:apply-templates select="*"/>
				<xsl:text>END:DAYLIGHT
</xsl:text>
			</xsl:for-each>
			<xsl:text>END:VTIMEZONE
</xsl:text>
		</xsl:for-each>
		<xsl:text>END:VCALENDAR
</xsl:text>
	</xsl:template>
	<xsl:template match="*">
		<xsl:value-of select="translate(local-name(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>
		<xsl:for-each select="@xml:lang">
			<xsl:call-template name="convert-lang"/>
		</xsl:for-each>
		<xsl:variable name="LangName">lang</xsl:variable>
		<xsl:for-each select="@*">
			<xsl:variable name="ParamName" select="local-name()"/>
			<xsl:choose>
				<xsl:when test="$ParamName!=$LangName">;<xsl:value-of select="translate(local-name(), 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ')"/>="<xsl:value-of select="."/>"</xsl:when>
			</xsl:choose>
		</xsl:for-each>:<xsl:call-template name="data-lf-wrap">
			<xsl:with-param name="sometext" select="."/>
		</xsl:call-template>
		<xsl:text>
</xsl:text>
	</xsl:template>
	<xsl:template name="data-lf-wrap">
		<xsl:param name="sometext"/>
		<xsl:variable name="lf">
			<xsl:text>
</xsl:text>
		</xsl:variable>
		<xsl:choose>
			<xsl:when test="contains($sometext,$lf)">
				<xsl:value-of select="substring-before($sometext,$lf)"/>
				<xsl:text>
 </xsl:text>
				<xsl:call-template name="data-lf-wrap">
					<xsl:with-param name="sometext" select="substring-after($sometext,$lf)"/>
				</xsl:call-template>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$sometext"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="convert-lang">
		<xsl:text>;LANGUAGE="</xsl:text>
		<xsl:value-of select="."/>
		<xsl:text>"</xsl:text>
	</xsl:template>
</xsl:stylesheet>
