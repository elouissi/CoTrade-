export interface LoginRequest {
  username: string;
  password: string;
}

export interface AuthResponse {
  access_token: string;
  // Ajoutez d'autres champs selon la réponse de votre API
}

export interface UserProfile {
  id?: string;  username?: string;
  email?: string;
  // Ajoutez d'autres champs selon vos besoins
}
