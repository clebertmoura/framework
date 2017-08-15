<!-- sidebar menu: : style can be found in sidebar.less -->
<ul class="sidebar-menu">
	<li class="treeview">
		<a href="">
			<i class="fa fa-edit"></i><span>Cadastros</span><i class="fa fa-angle-left pull-right"></i>
		</a>
		<!-- Copiar apenas os itens <li> das entidades que deseja incluir no menu. -->
		<ul class="treeview-menu">
		<#list entitiesMetamodel as entity>
			<li access="${entity.simpleName}.list,${entity.simpleName}.view">
				<a href="#/${entity.simpleName}"><i class="fa fa-circle-o"></i>
					<span>${entity.simpleName}</span>
				</a>
			</li>	
		</#list>
		</ul>
	</li>
</ul>