Vue.component("ProfileFishingInstructor", {
    data: function() {
        return {
            activeUser:"",
        }
    },
    template :`
    <div>
    <div class="container-fluid my-container">
    <div class="row my-row  justify-content-around">
        <div class="col-sm-6 my-col">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Ime:</span>
            <input type="text" id="nameInput"  class="form-control" placeholder="Unesite ime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Prezime:</span>
            <input type="text" id="lastnameInput"  class="form-control" placeholder="Unesite prezime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">E-mail adresa:</span>
            <input type="text" id="usernameInput" class="form-control" placeholder="Unesite korisničko ime...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Lozinka:</span>
            <input type="password" id="passwordInput"  class="form-control" placeholder="Unesite lozinku...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Adresa:</span>
            <input type="text" id="addressInput"   class="form-control" placeholder="Unesite adresu (ulica, broj)...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Grad:</span>
            <input type="text" id="birthDateInput" class="form-control" placeholder="Unesite grad...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Država:</span>
            <input type="text" id="birthDateInput" class="form-control" placeholder="Unesite državu...">
            <span class="input-group-text" id="basic-addon1" style="margin-top: 2%;">Broj telefona:</span>
            <input type="text" id="birthDateInput"  class="form-control" placeholder="Unesite broj telefona...">
        </div>
        <div class="col-sm-6 my-col">
            <div class="row my-row" style="margin-top: 2%;">
                <div class="col col-sm-3">
                    <input type="date" class="form-control" placeholder="Datum-od">
                      <span class="input-group-text" id="basic-addon1">Datum-od</span>
                  </div>
                  <div class="col col-sm-3">
                    <input type="time" class="form-control" placeholder="Datum-od">
                      <span class="input-group-text" id="basic-addon1">Vreme-od</span> 
                  </div>
                  <div class="col col-sm-3">
                    <input type="date"class="form-control" placeholder="Datum-do">
                      <span class="input-group-text" id="basic-addon1">Datum-do</span>
                  </div>
                  <div class="col col-sm-3">
                    <input type="time" class="form-control" placeholder="Datum-od">
                      <span class="input-group-text" id="basic-addon1">Vreme-do</span> 
                  </div>
            </div>
            <div class="row my-row" style="margin-top: 2%;">
                <div class="col col-sm-4">
                  </div>
                <div class="col col-sm-4">
                    <button type="button" style="margin-top: 2%; margin-left: 7%;" class="btn btn-secondary btn-lg">Promeni dostupnost</button>
                  </div>
                  <div class="col col-sm-4">
                    <button type="button" style="margin-top: 2%; margin-left: 7%;" class="btn btn-primary btn-lg">Sačuvaj dostupnost</button>
                  </div>
            </div>
            <div style="margin-top: 5%; margin-left: 15%;">
                <img src="../images/myinfo.png" height="450" width="600">
            </div>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-secondary btn-lg">Promeni podatke</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-primary btn-lg">Sačuvaj izmene</button>
            <button type="button" style="margin-top: 5%; margin-left: 7%;" class="btn btn-danger btn-lg">Zahtev za brisanje naloga</button>
        </div>
    </div>
</div>
</div>
</div>	  
    	`
    	,
    methods: {
    
    },
    mounted(){
    },

});