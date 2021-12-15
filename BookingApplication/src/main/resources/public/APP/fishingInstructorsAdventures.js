Vue.component("FishingInstructorsAdventures", {
    data: function() {
        return {
            activeUser:"",
            newAdventure : {name: "", addres: "", description: "", instructorBiography: "", photo: "", maxAmountOfPeople: 1, 
            behaviourRules: "", equipment: "", priceAndInfo: "", cancellingPrecentage: 0},
        }
    },
    template :`
    <div>
    <div style="margin-top: 2%;" class="container-fluid">
        <div style="margin-left: 5%;" class="row">
          <div class="col col-sm-3">
            <input type="text" class="form-control" placeholder="Naziv">
          </div>
          <div class="col col-sm-2">
            <button type="button"class="btn btn-success">Pretrazi usluge</button>
          </div>
          <div class="col col-sm-4">
          </div>
          <div class="col col-sm-2">
            <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#newAdventure">Dodaj novu uslugu</button>
          </div>
          <div class="col col-sm-1">
          </div>
          </div>

          <div class="modal fade" id="newAdventure" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
      <div class="modal-content">
      <div class="modal-content">
        <div class="modal-header">
          <h2 class="modal-title" style="color: #5cb85c;" id="exampleModalLongTitle">Nova avantura</h2>
          <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
        </div>
          <div class="container-fluid my-container">
          <div class="row my-row  justify-content-around">
            <div class="col-sm-4 my-col">
                <span style="margin-top: 2%;" class="input-group-text">Naziv:</span>
                <input type="text" id="nameInput" class="form-control"  placeholder="Unesite naziv...">
                <span class="input-group-text" style="margin-top: 3%;">Adresa:</span>
                <input type="text" id="lastnameInput" class="form-control" placeholder="Unesite adresu...">
                <span class="input-group-text" style="margin-top: 3%;">Opis:</span>
                <textarea class="form-control" id="usernameInput" rows="6" placeholder="Unesite opis..."></textarea>
                <span class="input-group-text" style="margin-top: 3%;">Pravila ponasanja:</span>
                <textarea class="form-control" id="usernameInput" rows="4" placeholder="Unesite pravila ponasanja..."></textarea>
                <span class="input-group-text" style="margin-top: 3%;">Oprema:</span>
                <textarea class="form-control" id="usernameInput" rows="4" placeholder="Unesite opremu..."></textarea>
              </div>
              <div class="col-sm-4 my-col">
                
                <span style="margin-top: 2%;" class="input-group-text">Maksimalni broj osoba:</span>
                <input type="number" min="1" max="30" class="form-control" placeholder="Unesite maksimalni broj osoba...">
                <span class="input-group-text" style="margin-top: 3%;" id="basic-addon1">Uslovi otkazivanja:</span>
                <select class="form-select" id="managerComboBox" aria-label="Example select with button addon">
                  <option>Besplatno</option>
                  <option>Procenat uplate</option>
                </select>
                <span class="input-group-text" style="margin-top: 3%;">Cenovnik:</span>
                <textarea class="form-control" id="usernameInput" rows="6" placeholder="Unesite cenovnik..."></textarea>
                <span class="input-group-text" style="margin-top: 3%;">Biografija instruktora:</span>
                <textarea class="form-control" id="usernameInput" rows="4" placeholder="Unesite biografiju..."></textarea>
                <span class="input-group-text" style="margin-top: 3%;">Informacije o dodatnim uslugama:</span>
                <textarea class="form-control" id="usernameInput" rows="4" placeholder="Unesite info o dodatnim uslugama..."></textarea>
              </div>
              <div class="col-sm-4 my-col">
                <div style="margin-top: 19%; margin-left: 12%;" class="col-sm-6 my-col">
                  <label class="input-group-text" for="inputGroupFile01">Izbor slike:</label>
                  <input id="uploadImage" name="myPhoto" onchange="PreviewImage();" type="file" accept="image/png, image/jpeg" class="form-control">
                  <div style="margin-top: 7%;">
                    <img  id="uploadPreview" style="width: 400px; height: 280px;" />
                  </div>
              </div>
                <button type="button" style="margin-top: 20%; margin-right: 20%; margin-left: 35%;" class="btn btn-success btn-lg">Dodaj uslugu</button>
              </div>
            </div>
        </div>
        </div>
          </div>
        </div>
      </div>

        <div class="container-fluid" style="margin-top: 2%; margin-left: 4mm;">
            <div class="row row-cols-1 row-cols-md-4 g-4">
              <div class="col">
                <div class="card" style="width: 93%">
                  <img src="../images/fishing.jpg" width="300" height="220" class="card-img-top" alt="...">
                  <div class="card-body">
                    <h5 class="card-title">Ime usluge</h5>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                    <li class="list-group-item">Adresa</li>
                    <li class="list-group-item">Max osoba</li>
                    <li class="list-group-item">4.5/5</li>
                  </ul>
                  <div class="card-body">
                    <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                  </div>
                </div>
              </div>
              <div class="col">
                <div class="card" style="width: 93%">
                  <img src="../images/fishing.jpg" width="300" height="220" class="card-img-top" alt="...">
                  <div class="card-body">
                    <h5 class="card-title">Ime usluge</h5>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                    <li class="list-group-item">Adresa</li>
                    <li class="list-group-item">Max osoba</li>
                    <li class="list-group-item">Rejting recimo</li>
                  </ul>
                  <div class="card-body">
                    <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                  </div>
                </div>
              </div>
              <div class="col">
                <div class="card" style="width: 93%">
                  <img src="../images/fishing.jpg" width="300" height="220" class="card-img-top" alt="...">
                  <div class="card-body">
                    <h5 class="card-title">Ime usluge</h5>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                    <li class="list-group-item">Adresa</li>
                    <li class="list-group-item">Max osoba</li>
                    <li class="list-group-item">Rejting recimo</li>
                  </ul>
                  <div class="card-body">
                    <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                  </div>
                </div>
              </div>
              <div class="col">
                <div class="card" style="width: 93%">
                  <img src="../images/fishing.jpg" width="300" height="220" class="card-img-top" alt="...">
                  <div class="card-body">
                    <h5 class="card-title">Ime usluge</h5>
                  </div>
                  <ul class="list-group list-group-flush">
                    <li class="list-group-item">Opis ce biti malo veci pa da vidimo kako to izgleda na kartici</li>
                    <li class="list-group-item">Adresa</li>
                    <li class="list-group-item">Max osoba</li>
                    <li class="list-group-item">Rejting recimo</li>
                  </ul>
                  <div class="card-body">
                    <button style="margin-left: 2%;" type="button" id="showArticles" class="btn btn-secondary">Informacije</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-primary">Izmeni</button>
                    <button style="margin-left: 8%;" type="button" id="showArticles" class="btn btn-danger">Obrisi</button>
                  </div>
                </div>
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