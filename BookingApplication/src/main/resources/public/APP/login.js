Vue.component("Login", {
    data: function() {
        return {
			email:"",
			password:""
        }
    },
    template:`  
	<div>
		<div>
			<form method="POST" @submit.prevent = "submitForm">
			<div class="forma container">
				<h1>Login</h1>
				<hr>
				<label class="col-sm-2 col-form-label" for="email"><b>Email</b></label>
				<input class="col-sm-2 col-form-control" type="text" v-model="email" required>
				<br>
				<label class="col-sm-2 col-form-label" for="password"><b>Password</b></label>
				<input class="col-sm-2 col-form-control" type="password" v-model="password" required>
				<br><br><br>
				<button class="button" type="submit">Login</button>
				<br>
				<div class="container signin">
					<p>Nemate nalog? <a href="#/register">Register</a>.</p>
			  	</div>
			</div>
			
			</form>
		</div>
        <a id="logout" v href="/#/logout">Logout</a>
	</div>
    `       
        ,
	 methods:{
		submitForm:function(){
			axios
                .post('/registration/login/' + this.email + "/" + this.password)
				.then(response => {
					if(response.data == null || response.data == ""){
						Swal.fire({
							icon: 'error',
							title: 'Error',
							text: 'Wrong username or password',
						  })
					}else{
						localStorage.setItem('activeUser',JSON.stringify(response.data))
						localStorage.removeItem('email') 
						this.$router.push('/')
						window.location.reload()
					}
				})
				.catch(error=>{
                    console.log("Greska.")	
					alert("Uneti nevalidni ili nepostojeci parametri, pokusajte ponovo.")
					window.location.reload()
                    
				})
            }
        }
        
    });
