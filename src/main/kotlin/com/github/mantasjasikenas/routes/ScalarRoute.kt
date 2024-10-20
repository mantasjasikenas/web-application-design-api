package com.github.mantasjasikenas.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.scalarRoute(apiUrl: String) {
    get("/scalar") {
        call.respondText(contentType = ContentType.Text.Html) {
            """
            <!doctype html>
                <html>
                  <head>
                    <title>Rest API Reference</title>
                    <meta charset="utf-8" />
                    <meta
                      name="viewport"
                      content="width=device-width, initial-scale=1" />
                      <link
                        rel="icon"
                        type="image/png"
                        href="https://scalar.com/favicon.png" />
                  </head>
                  <body>
                    <script
                      id="api-reference"
                      data-url="$apiUrl"></script>
                      
                      <script>
                          var configuration = {
                            theme: 'mars',
                          }
                    
                          document.getElementById('api-reference').dataset.configuration =
                            JSON.stringify(configuration)
                      </script>
                      
                    <script src="https://cdn.jsdelivr.net/npm/@scalar/api-reference"></script>
                  </body>
                </html>    
            """.trimIndent()
        }
    }
}