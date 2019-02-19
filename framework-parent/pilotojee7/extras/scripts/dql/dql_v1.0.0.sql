-- PRINCIPALS query
SELECT senha FROM USUARIO_LOGIN WHERE USUALOGIN_NM_LOGIN = ?

-- ROLES query (login com e-mail)
SELECT perm.perm_nm_nome, 'Roles' FROM ( 
	SELECT p.papel_nm_nome as perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.papel p 
	INNER JOIN dbarchetype01.usualogin_papel up ON up.papel_id = p.papel_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = up.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.papel_permissao pp ON pp.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.papel pap1 ON pap1.papel_id = pp.papel_id   
	INNER JOIN dbarchetype01.usualogin_papel up ON up.papel_id = pap1.papel_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = up.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.grupo_permissao gp ON gp.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.grupo g1 ON g1.grupo_id = gp.grupo_id 
	INNER JOIN dbarchetype01.usualogin_grupo ug ON ug.grupo_id = g1.grupo_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = ug.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.usualogin_perm_adic upa ON upa.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = upa.usualogin_id 
	MINUS 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.usualogin_perm_neg upn ON upn.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = upn.usualogin_id
) perm, dbarchetype01.usuario_login usu1 
WHERE perm.usualogin_id = usu1.usualogin_id AND usu1.usualogin_nm_login = '11111111111';

-- ROLES query (login com certificado)
SELECT perm.perm_nm_nome, 'Roles' FROM ( 
	SELECT p.papel_nm_nome as perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.papel p 
	INNER JOIN dbarchetype01.usualogin_papel up ON up.papel_id = p.papel_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = up.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.papel_permissao pp ON pp.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.papel pap1 ON pap1.papel_id = pp.papel_id   
	INNER JOIN dbarchetype01.usualogin_papel up ON up.papel_id = pap1.papel_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = up.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.grupo_permissao gp ON gp.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.grupo g1 ON g1.grupo_id = gp.grupo_id 
	INNER JOIN dbarchetype01.usualogin_grupo ug ON ug.grupo_id = g1.grupo_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = ug.usualogin_id 
	UNION 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.usualogin_perm_adic upa ON upa.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = upa.usualogin_id 
	MINUS 
	SELECT p.perm_nm_nome, usu.usualogin_id 
	FROM dbarchetype01.permissao p 
	INNER JOIN dbarchetype01.usualogin_perm_neg upn ON upn.perm_id = p.perm_id 
	INNER JOIN dbarchetype01.usuario_login usu ON usu.usualogin_id = upn.usualogin_id
) perm, dbarchetype01.usuario_login usu1, certificado cert
WHERE perm.usualogin_id = usu1.usualogin_id AND usu1.usualogin_id = cert.usualogin_id AND cert.certif_nm_commonname = 'CN=Cleber Tavares de Moura:04771628432, OU=SETIC, O=TJPE, ST=Pernambuco, C=BR';