describe('Sauce Demo Buy Item', () => {
    beforeEach(() => {
      cy.visit('/');
    });
  
    it('should log in, add an item to the cart, and proceed to checkout', () => {
      // Log in
      cy.get('#user-name').type('standard_user');
      cy.get('#password').type('secret_sauce');
      cy.get('#login-button').click();
  
      // Verify successful login by checking URL
      cy.url().should('include', '/inventory.html');
  
      // Add the first item to the cart
      cy.get('.inventory_item').first().find('button').click();
  
      // Go to the cart
      cy.get('.shopping_cart_link').click();
  
      // Verify item is in the cart
      cy.get('.cart_item').should('have.length', 1);
  
      // Proceed to checkout
      cy.get('#checkout').click();
  
      // Fill in checkout information
      cy.get('#first-name').type('John');
      cy.get('#last-name').type('Doe');
      cy.get('#postal-code').type('12345');
      cy.get('#continue').click();
  
      // Verify we are on the overview page
      cy.url().should('include', '/checkout-step-two.html');
  
      // Finish the purchase
      cy.get('#finish').click();
  
      // Verify order confirmation
      cy.get('.complete-header').should('contain', 'Thank you for your order!');
    });
  });
  