Vue.component("Navbar", {
    data: function() {
        return {
			activeUser:null

        }
    },
    template:`  
    <nav class="topnav">
	  <a class="active" href="/#/">Home</a>
		<div v-if="(activeUser===null)">
			<a href="#">Something</a>
		</div>
	  <div class="topnav-right dropdown1" v-if="(activeUser !== null)">
	  <button class="dropbtn1">Something
		<i class="fa fa-caret-down"></i>
	  </button>
	  <div class="dropdown-content1" >
		  <div v-if="(activeUser.role==='admin')">
		  	<a href="#/requestDeleteAcc">Requsts for account delete</a>
		  </div>
		  <div v-if="(activeUser.role==='admin')">
		  	<a href="#/complaints">Complaints</a>
		  </div>
			<div v-if="(activeUser.role==='admin') || (activeUser.role==='client')">
			  <a href="#">Something</a>
			</div>
	  </div>
		</div>
	  <a href="#contact">Contact</a>
		<div class="topnav-right" v-if="(activeUser===null)">
	    	<a href="/#/login">Login</a>
	    	<a href="/#/register">Register</a>
  		</div>
		<div class="topnav-right dropdown1" v-if="(activeUser !== null)">
		  <button class="dropbtn1">Profile
			<i class="fa fa-caret-down"></i>
		  </button>
		  <div class="dropdown-content1" >
		  	<div v-if="(activeUser.role==='client')">
			  <a href="/#/profileClient">My profile</a>
  			</div>
			<div v-if="(activeUser.role==='client')">
			  <a href="/#/sendComplaint">Write a complaint</a>
  			</div>
			<a href="/#/logout">Logout</a>
		  </div>
		</div>
		  
		<div class="topnav-right" v-if="(activeUser !== null)">
		    <a id="logout" class="nav-link" href="/#/logout">Logout</a>
		</div>
		<div class="topnav-right dropdown1" v-if="(activeUser !== null) && (activeUser.role==='admin')">
            <button class="dropbtn1">Something
            <i class="fa fa-caret-down"></i>
            </button>
		<div class="dropdown-content1" >
			<div v-if="(activeUser.role==='admin')">
			    <a class="nav-link" href="/#">Something</a>
			</div>
			<div v-if="(activeUser.role==='admin')">
			    <a class="nav-link" href="/#">Something</a>
			</div>
		</div>
	  	</div>
	</div>
	</nav>
    `       
        ,
    mounted(){
        this.activeUser=JSON.parse(localStorage.getItem('activeUser'))
    },
	methods:{

        
	}
});