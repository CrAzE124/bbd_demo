const amqplib = require('amqplib/callback_api');

amqplib.connect(`amqp://localhost:5672`, (err, connection) => {
  connection.createChannel((err, ch) => {
    ch.assertExchange('greetings', 'topic', {durable: true});

    ch.assertQueue('', {exclusive: true}, (err, q) => {
      console.log(' [*] Waiting for messages in %s..', q.queue);

      ch.bindQueue(q.queue, 'greetings', '#');

      ch.consume(q.queue, (msg) => {
        console.log(' [*] %s', msg.content.toString());
      }, {noAck: true});
    });
  });
});