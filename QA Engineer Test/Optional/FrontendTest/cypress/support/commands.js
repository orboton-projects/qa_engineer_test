// cypress/support/commands.js

Cypress.Commands.add('login', (username, password) => {
    cy.get('#user-name').type(username);
    cy.get('#password').type(password);
    cy.get('#login-button').click();
  });
  
  Cypress.Commands.add('addItemToCart', () => {
    cy.get('.inventory_item').first().find('button').click();
  });
  
  Cypress.Commands.add('checkout', (firstName, lastName, postalCode) => {
    cy.get('#first-name').type(firstName);
    cy.get('#last-name').type(lastName);
    cy.get('#postal-code').type(postalCode);
    cy.get('#continue').click();
  });
  