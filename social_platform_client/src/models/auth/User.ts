class User{
    username: string = "";
    email: string = "";    
    password: string = "";
    birthDate: string = "";
    lastLoginhDate: string = "";
    registrationDate: string = "";
    phoneNumber: string = "";
    profilePictureURL: string = "";
    userLocation: string = "";
    userCurrentLocation: string = "";

    constructor(
        username: string,
        email: string,
        password: string,
        birthDate: string,
        lastLoginDate: string,
        registrationDate: string,
        phoneNumber: string,
        profilePictureURL: string,
        userLocation: string,
        userCurrentLocation: string
    ){
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.lastLoginhDate = lastLoginDate;
        this.registrationDate = registrationDate;
        this.phoneNumber = phoneNumber;
        this.profilePictureURL = profilePictureURL;
        this.userLocation = userLocation;
        this.userCurrentLocation = userCurrentLocation;
    }
}