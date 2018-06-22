<h1>MyMovieApp - Version 0.1.0</h1>
<h2>Información General</h2>
<ul>
         <li>Titulo de versión: MyMovieApp</li>
         <li>Nombre de versión: 0.1.0</li>
         <li>Versión de Java: 7</li>
         <li>SDK Android Target : 27</li>
         <li>Mínima versión del SDK de Android: 17</li>
         <li>Versión del SDK de Android para compilar: 26</li>
         <li>Descripción: Aplicación que consume un Api de películas , permite clasificar y ver detalle de peliculas además de poder ver los avances. </li>

</ul>

<hr>

<h2>Capas de la aplicación</h2>
<ul>
         <li><b>Capa de Datos</b> : Es la capa en donde se obtienen todos los datos que necesita la aplicación (PersistencePopularData , PersistenceTopRatedData , PersistenceUpcomingData , PreferencesHelper , SaveDataHelper) </li>
         <li><b>Capa de Dominio </b> : Es la capa donde esta toda la lógica de negocio (Movie , MovieResponses , Video , VideoResponses)</li>
         <li><b>Capa de Presentación </b> : Es la capa en donde ocurre todo lo relacionado a cómo funcionan la vistas . Se usó por simplicidad el pratron MVC (BaseActivity , MainActivity , MovieDetailActivity , SortMovieOptionsDialogFragment) </li>
</ul>

<h2>Responsabilidad de clases</h2>
<ul>
         <li><b>MainActivity</b> : Consumir datos del Api y manejar la vista de listado de peliculas</li>
         <li><b>MovieDetailActivity</b> : Presentar el detalle de peliculas</li>
         <li><b>BaseActivity</b> : Configuracion de orientacion del app e inicializacion del contexto</li>
         <li><b>SortMovieOptionsDialogFragment</b> : Presentar los metodos de selección de películas </li>
         <li><b>Client</b> : Inicializar el cliente REST</li>
         <li><b>Service</b> : Manejo de las peticiones</li>
         <li><b>MoviesAdapter</b> : Puente entre la data de las peliculas y la vista</li>
         <li><b>VideoAdapter</b> : Puente entre la data de las trailers de las peliculas y la vista</li>
         <li><b>MovieViewHolderHelper</b> : Clase de ayuda para un mejor orden en mi proyecto</li>
         <li><b>PersistencePopularData</b> : Clase para almacenar los datos de la lista de peliculas populares en una base de datos local</li>
         <li><b>PersistenceTopRatedData</b> : Clase para almacenar los datos de la lista de mejores peliculas en una base de datos local</li>
         <li><b>PersistenceUpcomingData</b> : Clase para almacenar los datos de la lista de proximas peliculas en una base de datos local </li>
         <li><b>PreferencesHelper</b> : Clase de ayuda para un mejor orden en mi proyecto , se encarga de comunicar si la data ya esta en una base de datos local</li>
         <li><b>MovieViewHolderHelper</b> : Clase de ayuda para un mejor orden en mi proyecto</li>
         <li><b>SaveDataHelper</b> : Clase de ayuda para un mejor orden en mi proyecto , se encarga de manejar los keys con los cuales almacenaré la data en local</li>
         <li><b>SortMethodInterface</b> : Interfaz que permite la comunicación entre SortMovieOptionsDialogFragment y MainActivity con ello la actividad conoce la opción de selección elegida</li>
         <li><b>ApplicationConstants</b> : Clase de ayuda para un mejor orden en mi proyecto , se encarga de manejar constantes globales del proyecto</li>


</ul>

