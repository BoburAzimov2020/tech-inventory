import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './shelf-type.reducer';

export const ShelfTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const shelfTypeEntity = useAppSelector(state => state.shelfType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="shelfTypeDetailsHeading">Shelf Type</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{shelfTypeEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
            <UncontrolledTooltip target="name">Javon turlari</UncontrolledTooltip>
          </dt>
          <dd>{shelfTypeEntity.name}</dd>
          <dt>
            <span id="info">Info</span>
          </dt>
          <dd>{shelfTypeEntity.info}</dd>
          <dt>Obyekt</dt>
          <dd>{shelfTypeEntity.obyekt ? shelfTypeEntity.obyekt.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/shelf-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Назад</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shelf-type/${shelfTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Изменить</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ShelfTypeDetail;
