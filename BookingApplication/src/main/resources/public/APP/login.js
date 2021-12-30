Vue.component("Login", {
    data: function() {
        return {
			email:"",
			password:""
        }
    },
    template:`  
	<div class="container">
		<div class="row">
			<div class="col-lg-3 col-md-2"></div>

			<div class="col-lg-6 col-md-8 login-box">
				<form method="POST" @submit.prevent = "submitForm">
					<div class="forma container">
						<div class="col-lg-12 login-key">
							<i class="bi bi-key" aria-hidden="true"></i>
						</div>
						<div class="col-lg-12 login-title">LOGIN</div><br>
						<div class="form-group">
							<label class="form-control-label" for="email"><b>Email</b></label>
							<input class="form-control inputPass" type="text" v-model="email" required>
						</div>
						<br>


						<div class="form-group">
							<label class="form-control-label" for="password"><b>Password</b></label>
							<input class="form-control inputPass" type="password" v-model="password" required>
						</div>
						<br>
						<button class="button" type="submit">Login</button>
						<br><br>
						<div class="container signin">
							<p>Nemate nalog? <a href="#/register">Register</a>.</p>
						</div>
					</div>
				</form>
			</div>
		</div>	

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
