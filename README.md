# Shortly - A Full-Stack URL Shortener

**Live Demo:** [**https://url-shortener-fullstack-lovat.vercel.app/**]
## 📖 About The Project

Shortly is a full-stack URL shortener application built from scratch. It allows users to shorten long URLs, manage their links, and track click analytics. The project features a secure, token-based authentication system and rate limiting for both anonymous and registered users.

This project was built as a comprehensive, hands-on learning experience covering the entire development lifecycle, from local setup to a multi-platform cloud deployment.

---

## ✨ Features

* **User Authentication:** Secure user registration and login with JWT (JSON Web Token) authentication.
* **URL Shortening:**
    * Anonymous users can shorten up to 3 URLs (tracked by IP).
    * Registered users can manage their own library of links.
* **Link Management Dashboard:** A user-specific dashboard to view, copy, and delete shortened URLs.
* **Click Analytics:** Simple tracking for the number of clicks on each shortened link.
* **Security:**
    * **Bot Protection:** Google reCAPTCHA v2 on the public shortening form.
    * **Rate Limiting:** Protects against abuse for anonymous users.
* **Responsive UI/UX:** A clean, modern, and mobile-friendly interface with smooth animations and user feedback.
* **Automatic Cleanup:** Anonymous links are automatically deleted after 30 days using a MongoDB TTL index.

---

## 🛠️ Tech Stack

This project uses a modern, production-ready technology stack:

* **Frontend:**
    * **React (Vite):** A fast, modern UI library.
    * **React Router:** For client-side routing.
    * **Axios:** For making API requests.
    * **Framer Motion:** For smooth page transitions and animations.
    * **react-hot-toast:** For professional notifications.
* **Backend:**
    * **Java 17 & Spring Boot:** For building a robust and scalable REST API.
    * **Spring Security:** For handling JWT-based authentication and authorization.
    * **Spring Data MongoDB:** For database interaction.
* **Database:**
    * **MongoDB Atlas:** A fully-managed, cloud-hosted NoSQL database.
* **Deployment:**
    * **Docker:** The backend application is containerized for consistency.
    * **Render:** For hosting the backend Docker container.
    * **Vercel:** For hosting the frontend React application.

---

## 🚀 Running Locally

To run this project on your local machine, follow these steps:

### Prerequisites
- Java JDK 17 or later
- Apache Maven
- Node.js and npm
- A MongoDB Atlas account
- A Google reCAPTCHA v2 account

### Backend Setup
1.  Navigate to the `backend` directory: `cd backend`
2.  Create a `.env` file in the `backend` directory and add the following variables:
    ```env
    MONGO_URI=your_mongodb_atlas_connection_string
    JWT_SECRET=your_local_jwt_secret
    RECAPTCHA_SECRET_KEY=your_google_recaptcha_secret_key
    ```
3.  Run the application:
    ```sh
    mvn spring-boot:run
    ```
    The backend will be running on `http://localhost:8080`.

### Frontend Setup
1.  Navigate to the `frontend` directory: `cd frontend`
2.  Install dependencies: `npm install`
3.  Create a `.env.local` file in the `frontend` directory and add the following variables:
    ```env
    VITE_API_BASE_URL=http://localhost:8080
    VITE_RECAPTCHA_SITE_KEY=your_google_recaptcha_site_key
    ```
4.  Run the development server:
    ```sh
    npm run dev
    ```
    The frontend will be running on `http://localhost:5173`.